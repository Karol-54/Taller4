package com.jaimes.comjaimesmovielibrary.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.jaimes.comjaimesmovielibrary.db.AppDatabase
import com.jaimes.comjaimesmovielibrary.model.Movie
import com.jaimes.comjaimesmovielibrary.repository.MovieRepository
import kotlinx.coroutines.launch

class MovieViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: MovieRepository
    val movies: LiveData<List<Movie>>

    private val _selectedMovie = MutableLiveData<Movie?>()
    val selectedMovie: LiveData<Movie?> = _selectedMovie

    init {
        val movieDao = AppDatabase.getDatabase(application).movieDao()
        repository = MovieRepository(movieDao)
        movies = repository.getMovies()
    }

    fun getMovie(id: Int): LiveData<Movie> {
        return repository.getMovie(id)
    }

    fun addMovie(movie: Movie) {
        viewModelScope.launch {
            repository.insert(movie)
        }
    }

    fun updateMovie(movie: Movie) {
        viewModelScope.launch {
            repository.update(movie)
        }
    }

    fun deleteMovie(movie: Movie) {
        viewModelScope.launch {
            repository.delete(movie)
        }
    }

    fun selectMovie(movie: Movie) {
        _selectedMovie.value = movie
    }
}