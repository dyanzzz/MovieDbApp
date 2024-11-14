package com.rakamin.moviedbapp.features.seemore

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.rakamin.moviedbapp.databinding.FragmentSeeMoreBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SeeMoreFragment: Fragment() {
    private lateinit var _binding: FragmentSeeMoreBinding
    private val binding get() = _binding
    
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentSeeMoreBinding.inflate(inflater, container, false)
        return binding.root
    }
}
