package com.demo.roudykk.demoapp.ui.activity

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.os.Bundle
import com.demo.roudykk.demoapp.R
import com.demo.roudykk.demoapp.api.models.Movie
import com.demo.roudykk.demoapp.db.models.MovieViewModel

class WatchLaterActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_watch_later)

        val movieViewModel = ViewModelProvider.AndroidViewModelFactory(application)
                .create(MovieViewModel::class.java)

        movieViewModel.getMovies()?.observe(this, Observer<ArrayList<Movie>> { movies ->
            //TODO do something with movies
        })
    }
}