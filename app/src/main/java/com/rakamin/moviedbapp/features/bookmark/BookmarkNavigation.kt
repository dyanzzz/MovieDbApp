package com.rakamin.moviedbapp.features.bookmark

import androidx.navigation.fragment.findNavController

fun BookmarkFragment.gotoDetail(movieId: String) {
    val action = BookmarkFragmentDirections.actionBookmarkFragmentToDetailFragment(movieId)
    findNavController().navigate(action)
}
