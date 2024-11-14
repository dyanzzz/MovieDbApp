package com.rakamin.moviedbapp.features.home

import androidx.navigation.fragment.findNavController
import com.rakamin.moviedbapp.domain.model.response.MovieResponse

fun HomeFragment.gotoDetail(movie: MovieResponse) {
    val action = HomeFragmentDirections.actionHomeFragmentToDetailFragment(movie.id.toString())
    findNavController().navigate(action)
}

fun HomeFragment.gotoSeeMore(type: String) {
    val action = HomeFragmentDirections.actionHomeFragmentToSeeMoreFragment(type)
    findNavController().navigate(action)
}
