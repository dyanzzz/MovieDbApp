package com.rakamin.moviedbapp.uiadapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.rakamin.moviedbapp.BuildConfig
import com.rakamin.moviedbapp.databinding.ItemVerticalBinding
import com.rakamin.moviedbapp.domain.model.response.MovieResponse

class PopularAdapter: ListAdapter<MovieResponse, PopularAdapter.ViewHolder>(DiffCallback) {
    var itemOnClick: (MovieResponse) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemVerticalBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(
        private val binding: ItemVerticalBinding
    ): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: MovieResponse) = with(binding) {
            tvTitle.text = item.title
            "${item.voteAverage}/${item.voteCount} IMDb".also {
                tvRating.text = it
            }

            tvReleaseDate.text = item.releaseDate

            val url = BuildConfig.BASE_URL_IMG + item.posterPath
            Glide.with(this.root)
                .load(url)
                .centerCrop()
                .transition(DrawableTransitionOptions.withCrossFade())
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(imgPoster)

            root.setOnClickListener {
                itemOnClick.invoke(item)
            }
        }
    }

    object DiffCallback: DiffUtil.ItemCallback<MovieResponse>() {
        override fun areItemsTheSame(oldItem: MovieResponse, newItem: MovieResponse): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: MovieResponse, newItem: MovieResponse): Boolean {
            return oldItem.id == newItem.id
        }

    }


}
