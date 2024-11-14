package com.rakamin.moviedbapp.features.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.rakamin.moviedbapp.databinding.FragmentHomeBinding
import com.rakamin.moviedbapp.domain.model.ResponseResult
import com.rakamin.moviedbapp.uiadapter.NowPlayingAdapter
import com.rakamin.moviedbapp.uiadapter.PopularAdapter
import com.rakamin.moviedbapp.utils.FragmentExtension.setToolbar
import com.rakamin.moviedbapp.utils.NOW_PLAYING
import com.rakamin.moviedbapp.utils.POPULAR
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment: Fragment() {

    private val viewModel: HomeViewModel by viewModels()

    private lateinit var _binding: FragmentHomeBinding
    private val binding get() = _binding

    private lateinit var nowPlayingAdapter: NowPlayingAdapter
    private lateinit var popularAdapter: PopularAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initToolbar()
        onSetupUi()
        onObserve()
    }

    private fun initToolbar() = with(binding) {
        setToolbar(toolbarMain.toolbar, false)
        toolbarMain.title.visibility = View.GONE
    }
    
    private fun onSetupUi() = with(binding) {
        viewModel.getNowPlayingMovies()
        
        nowPlayingAdapter = NowPlayingAdapter()
        popularAdapter = PopularAdapter()
        
        nowPlayingAdapter.itemOnClick = {
            gotoDetail(it)
        }
        
        rvNowPlaying.adapter = nowPlayingAdapter
        btnSeeMorePopular.setOnClickListener {
            gotoSeeMore(POPULAR)
        }
        btnSeeMoreNowPlaying.setOnClickListener {
            gotoSeeMore(NOW_PLAYING)
        }
    }
    
    private fun onObserve() {
        viewModel.nowPlayingMovies.observe(viewLifecycleOwner) { movie ->
            when (movie) {
                is ResponseResult.Loading -> {
                    println("LOADING")
                }
                is ResponseResult.Error -> {
                    Toast.makeText(requireContext(), movie.throwable.message, Toast.LENGTH_SHORT).show()
                    println("Error")
                }
                is ResponseResult.Success -> {
                    nowPlayingAdapter.submitList(movie.data.slice(0..5))
                }
            }
        }
    }
}
