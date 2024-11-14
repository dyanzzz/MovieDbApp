package com.rakamin.moviedbapp.features.bookmark

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.rakamin.moviedbapp.R
import com.rakamin.moviedbapp.databinding.FragmentSeeMoreBinding
import com.rakamin.moviedbapp.uiadapter.BookmarkAdapter
import com.rakamin.moviedbapp.utils.FragmentExtension.setToolbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BookmarkFragment: Fragment() {

    private lateinit var _binding: FragmentSeeMoreBinding
    private val binding get() = _binding

    private val viewModel: BookmarkViewModel by viewModels()
    private lateinit var popularAdapter: BookmarkAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentSeeMoreBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initToolbar()


        observeData()
    }

    private fun initToolbar() = with(binding) {
        setToolbar(toolbarMain.toolbar, false)
        toolbarMain.logo.visibility = View.GONE
        toolbarMain.title.text = resources.getString(R.string.bookmark)
    }

    private fun observeData() {
        viewModel.getMovies()

        popularAdapter = BookmarkAdapter()
        binding.rvPopular.adapter = popularAdapter
        popularAdapter.itemOnClick = {
            gotoDetail(it.id.toString())
        }

        viewModel.listMovies.observe(viewLifecycleOwner) { result ->
            popularAdapter.submitList(result)
        }
    }
}
