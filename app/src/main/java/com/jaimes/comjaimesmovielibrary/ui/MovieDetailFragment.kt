package com.jaimes.comjaimesmovielibrary.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.jaimes.comjaimesmovielibrary.viewmodel.MovieViewModel
import com.jaimes.movielibrary.databinding.FragmentMovieDetailBinding

class MovieDetailFragment : Fragment() {

    private var _binding: FragmentMovieDetailBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MovieViewModel by activityViewModels()
    private val args: MovieDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMovieDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val movieId = args.movieId

        viewModel.getMovie(movieId).observe(viewLifecycleOwner) { movie ->
            movie?.let {
                binding.tvMovieTitle.text = it.title
                binding.tvMovieYear.text = "Año: ${it.year}"
                binding.tvMovieGenre.text = "Género: ${it.genre}"
                binding.tvMovieRating.text = "Rating: ${it.rating}/10"
                binding.checkBoxWatched.isChecked = it.watched

                binding.checkBoxWatched.setOnCheckedChangeListener { _, isChecked ->
                    viewModel.updateMovie(it.copy(watched = isChecked))
                }

                binding.btnEdit.setOnClickListener { _ ->
                    val action = MovieDetailFragmentDirections
                        .actionMovieDetailFragmentToMovieEditFragment(movieId = it.id)
                    findNavController().navigate(action)
                }

                binding.btnDelete.setOnClickListener { _ ->
                    viewModel.deleteMovie(it)
                    findNavController().navigateUp()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}