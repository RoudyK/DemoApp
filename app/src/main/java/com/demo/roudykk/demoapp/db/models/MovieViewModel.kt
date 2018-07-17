package com.demo.roudykk.demoapp.db.models

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import com.demo.roudykk.demoapp.api.models.Movie
import com.demo.roudykk.demoapp.db.MovieDatabase
import com.demo.roudykk.demoapp.db.dao.MovieDao

class MovieViewModel : AndroidViewModel, MovieDao {

    private var movies: LiveData<List<Movie>>? = null
    private var movieDao: MovieDao? = null

    constructor(application: Application) : super(application) {
        this.movieDao = MovieDatabase.getInstance(application)?.movieDao()
        this.movies = this.movieDao?.getAll()
    }

    override fun insert(vararg movies: Movie) {
        movies.forEach { movie ->
            this.movieDao?.insert(movie)
        }
    }

    override fun delete(id: Int) {
        this.movieDao?.delete(id)
    }

    override fun deleteAll() {
        this.movieDao?.deleteAll()
    }

    override fun getAll(): LiveData<List<Movie>> {
        return this.movieDao!!.getAll()
    }

}