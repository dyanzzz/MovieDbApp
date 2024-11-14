package com.rakamin.moviedbapp.features.seemore

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.navigation.fragment.navArgs
import com.rakamin.moviedbapp.databinding.FragmentSeeMoreBinding
import com.rakamin.moviedbapp.domain.model.ResponseResult
import com.rakamin.moviedbapp.domain.model.response.MovieResponse
import com.rakamin.moviedbapp.uiadapter.PopularAdapter
import com.rakamin.moviedbapp.utils.FragmentExtension.setToolbar
import com.rakamin.moviedbapp.utils.POPULAR
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SeeMoreFragment: Fragment() {
    private lateinit var _binding: FragmentSeeMoreBinding
    private val binding get() = _binding

    private val viewModel: SeeMoreViewModel by viewModels()
    private lateinit var popularAdapter: PopularAdapter

    private val args: SeeMoreFragmentArgs by navArgs()
    
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentSeeMoreBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initToolbar()

        popularAdapter = PopularAdapter()
        binding.rvPopular.adapter = popularAdapter
        popularAdapter.itemOnClick = {
            gotoDetail(it)
        }

        if (args.type == POPULAR) {
            viewModel.getMoviePopular()
            observeNowPlayingMovies(viewModel.listPopularMovies)
        } else {
            viewModel.getNowPlayingMovies()
            observeNowPlayingMovies(viewModel.listPlayingMovies)
        }
    }

    private fun observeNowPlayingMovies(list: LiveData<ResponseResult<List<MovieResponse>>>) {
        list.observe(viewLifecycleOwner) { movie ->
            when (movie) {
                is ResponseResult.Loading -> {
                    println("Loading")
                }
                is ResponseResult.Error -> {
                    Toast.makeText(requireContext(), movie.throwable.message, Toast.LENGTH_SHORT).show()
                }
                is ResponseResult.Success -> {
                    popularAdapter.submitList(movie.data)
                }
            }
        }
    }

    private fun initToolbar() = with(binding) {
        setToolbar(toolbarMain.toolbar, true)
        toolbarMain.logo.visibility = View.GONE
        toolbarMain.title.text = args.type
    }
}
