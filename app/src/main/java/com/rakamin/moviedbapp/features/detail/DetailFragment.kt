package com.rakamin.moviedbapp.features.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.rakamin.moviedbapp.BuildConfig
import com.rakamin.moviedbapp.R
import com.rakamin.moviedbapp.databinding.FragmentDetailBinding
import com.rakamin.moviedbapp.domain.model.ResponseResult
import com.rakamin.moviedbapp.domain.model.response.DetailMovieResponse
import com.rakamin.moviedbapp.utils.FragmentExtension.setToolbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment: Fragment() {
    private lateinit var _binding: FragmentDetailBinding
    private val binding get() = _binding

    private val viewModel: DetailViewModel by viewModels()

    private val args: DetailFragmentArgs by navArgs()

    private var isBookmark = false
    private var detailMovie: DetailMovieResponse? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getDetailMovie(args.movieId)
        onObserve()

        viewModel.isBookmarked(args.movieId)
        observeBookmark()
    }

    private fun initToolbar(title: String) = with(binding) {
        setToolbar(toolbarMain.toolbar, true)
        toolbarMain.logo.visibility = View.GONE
        toolbarMain.title.text = title
    }
    
    private fun onObserve() {
        viewModel.detailMovie.observe(viewLifecycleOwner) { movie ->
            when (movie) {
                is ResponseResult.Loading -> println("Loading fragment")
                is ResponseResult.Error -> println("Error fragment")
                is ResponseResult.Success -> {
                    detailMovie = movie.data
                    
                    initToolbar(movie.data.title ?: "")
                    onSetupUi(movie.data)
                    
                    val url = BuildConfig.BASE_URL_IMG
                    Glide.with(this)
                        .load(url + movie.data.backdropPath)
                        .centerCrop()
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .into(binding.imgBackdrop)

                    Glide.with(this)
                        .load(url + movie.data.posterPath)
                        .centerCrop()
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .into(binding.imgPoster)
                }
            }
        }
    }

    private fun onSetupUi(movie: DetailMovieResponse) = with(binding) {
        tvTitle.text = movie.title
        tvDescription.text = movie.overview
        tvReleaseDate.text = movie.releaseDate
        tvStatus.text = movie.status
        tvRating.text = movie.voteAverage.toString()
        tvPopularity.text = movie.popularity.toString()
    }

    private fun observeBookmark() = with(binding){
        viewModel.isBookmark.observe(viewLifecycleOwner) { result ->
            isBookmark = result
            fabBookmark.setImageResource(
                if (result) R.drawable.ic_bookmark_added else R.drawable.ic_bookmark_border
            )
        }

        setupBookmark()
    }

    private fun setupBookmark() = with(binding) {
        fabBookmark.setOnClickListener {
            println(detailMovie.toString())
            detailMovie?.let { data -> viewModel.setBookmark(data, isBookmark) }
            isBookmark = !isBookmark
            fabBookmark.setImageResource(
                if (isBookmark) R.drawable.ic_bookmark_added else R.drawable.ic_bookmark_border
            )
        }
    }
}
