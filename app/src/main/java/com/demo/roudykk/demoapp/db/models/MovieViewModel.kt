package com.demo.roudykk.demoapp.db.models

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import com.demo.roudykk.demoapp.api.models.Movie
import com.demo.roudykk.demoapp.db.MovieDatabase
import com.demo.roudykk.demoapp.db.dao.MovieDao

class MovieViewModel : AndroidViewModel {
    private var movies: LiveData<ArrayList<Movie>>? = null
    private var movieDao: MovieDao? = null

    constructor(application: Application) : super(application) {
        this.movieDao = MovieDatabase.getInstance(application)?.movieDao()
        this.movies = this.movieDao?.getAll()
    }

    fun getMovies(): LiveData<ArrayList<Movie>>? {
        return this.movieDao?.getAll()
    }
}