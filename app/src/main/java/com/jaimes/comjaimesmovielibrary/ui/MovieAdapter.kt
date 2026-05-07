package com.jaimes.comjaimesmovielibrary.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.jaimes.comjaimesmovielibrary.model.Movie
import com.jaimes.movielibrary.R

class MovieAdapter(
    private val onMovieClick: (Movie) -> Unit
) : ListAdapter<Movie, MovieAdapter.MovieViewHolder>(MovieDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_movie, parent, false)
        return MovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvTitle: TextView = itemView.findViewById(R.id.tvMovieItemTitle)
        private val tvGenre: TextView = itemView.findViewById(R.id.tvMovieItemGenre)
        private val tvWatched: TextView = itemView.findViewById(R.id.tvMovieItemWatched)

        fun bind(movie: Movie) {
            tvTitle.text = movie.title
            tvGenre.text = "${movie.genre} • ${movie.year}"
            tvWatched.text = if (movie.watched) "✓ Vista" else "No vista"
            itemView.setOnClickListener { onMovieClick(movie) }
        }
    }

    class MovieDiffCallback : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: Movie, newItem: Movie) = oldItem == newItem
    }
}