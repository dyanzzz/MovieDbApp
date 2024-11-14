package com.rakamin.moviedbapp.features.seemore

import androidx.navigation.fragment.findNavController
import com.rakamin.moviedbapp.domain.model.response.MovieResponse

fun SeeMoreFragment.gotoDetail(data: MovieResponse) {
    val action = SeeMoreFragmentDirections.actionSeeMoreFragmentToDetailFragment(data.id.toString())
    findNavController().navigate(action)

}
