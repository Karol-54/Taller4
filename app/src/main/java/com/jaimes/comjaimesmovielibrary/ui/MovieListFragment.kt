package com.jaimes.comjaimesmovielibrary.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.jaimes.comjaimesmovielibrary.viewmodel.MovieViewModel
import com.jaimes.movielibrary.databinding.FragmentMovieListBinding

class MovieListFragment : Fragment() {

    private var _binding: FragmentMovieListBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MovieViewModel by activityViewModels()
    private lateinit var adapter: MovieAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMovieListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = MovieAdapter { movie ->
            viewModel.selectMovie(movie)
            val action = MovieListFragmentDirections
                .actionMovieListFragmentToMovieDetailFragment(movieId = movie.id)
            findNavController().navigate(action)
        }

        binding.recyclerViewMovies.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewMovies.adapter = adapter

        viewModel.movies.observe(viewLifecycleOwner) { movieList ->
            adapter.submitList(movieList)
            binding.tvEmptyMessage.visibility =
                if (movieList.isEmpty()) View.VISIBLE else View.GONE
        }

        binding.fabAddMovie.setOnClickListener {
            val action = MovieListFragmentDirections
                .actionMovieListFragmentToMovieEditFragment(movieId = -1)
            findNavController().navigate(action)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}