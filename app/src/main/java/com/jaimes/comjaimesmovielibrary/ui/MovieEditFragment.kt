package com.jaimes.comjaimesmovielibrary.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.jaimes.comjaimesmovielibrary.model.Movie
import com.jaimes.comjaimesmovielibrary.viewmodel.MovieViewModel
import com.jaimes.movielibrary.databinding.FragmentMovieEditBinding

class MovieEditFragment : Fragment() {

    private var _binding: FragmentMovieEditBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MovieViewModel by activityViewModels()
    private val args: MovieEditFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMovieEditBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val movieId = args.movieId

        // Modo edición: cargar datos existentes
        if (movieId != -1) {
            binding.tvEditTitle.text = "Editar Película"
            viewModel.getMovie(movieId).observe(viewLifecycleOwner) { movie ->
                movie?.let {
                    binding.etTitle.setText(it.title)
                    binding.etYear.setText(it.year.toString())
                    binding.etGenre.setText(it.genre)
                    binding.etRating.setText(it.rating.toString())
                    binding.checkBoxWatched.isChecked = it.watched
                }
            }
        }

        binding.btnSave.setOnClickListener {
            val title = binding.etTitle.text.toString().trim()
            val yearStr = binding.etYear.text.toString().trim()
            val genre = binding.etGenre.text.toString().trim()
            val ratingStr = binding.etRating.text.toString().trim()
            val watched = binding.checkBoxWatched.isChecked

            if (title.isEmpty() || yearStr.isEmpty() || genre.isEmpty() || ratingStr.isEmpty()) {
                Toast.makeText(requireContext(), "Completa todos los campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val movie = Movie(
                id = if (movieId == -1) 0 else movieId,
                title = title,
                year = yearStr.toInt(),
                genre = genre,
                rating = ratingStr.toFloat(),
                watched = watched
            )

            if (movieId == -1) {
                viewModel.addMovie(movie)
            } else {
                viewModel.updateMovie(movie)
            }

            findNavController().navigateUp()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}