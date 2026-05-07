package com.jaimes.comjaimesmovielibrary.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.jaimes.comjaimesmovielibrary.db.MovieDao
import com.jaimes.comjaimesmovielibrary.db.MovieEntity
import com.jaimes.comjaimesmovielibrary.model.Movie

class MovieRepository(private val movieDao: MovieDao) {

    fun getMovies(): LiveData<List<Movie>> {
        return movieDao.getAllMovies().map { entities ->
            entities.map { it.toMovie() }
        }
    }

    fun getMovie(id: Int): LiveData<Movie> {
        return movieDao.getMovieById(id).map { it.toMovie() }
    }

    suspend fun insert(movie: Movie) {
        movieDao.insert(movie.toEntity())
    }

    suspend fun update(movie: Movie) {
        movieDao.update(movie.toEntity())
    }

    suspend fun delete(movie: Movie) {
        movieDao.delete(movie.toEntity())
    }

    // ── Conversiones ──────────────────────────────────────────

    private fun MovieEntity.toMovie() = Movie(
        id = id,
        title = title,
        year = year,
        genre = genre,
        rating = rating,
        watched = watched
    )

    private fun Movie.toEntity() = MovieEntity(
        id = id,
        title = title,
        year = year,
        genre = genre,
        rating = rating,
        watched = watched
    )
}