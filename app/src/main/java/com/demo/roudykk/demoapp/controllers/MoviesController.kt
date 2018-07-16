package com.demo.roudykk.demoapp.controllers

import android.widget.RatingBar
import com.airbnb.epoxy.TypedEpoxyController
import com.demo.roudykk.demoapp.ListMovieBindingModel_
import com.demo.roudykk.demoapp.LoaderBindingModel_
import com.demo.roudykk.demoapp.R
import com.demo.roudykk.demoapp.api.model.Movie
import com.demo.roudykk.demoapp.extensions.applyTheme

class MoviesController(private val moviesListener: MoviesListener) : TypedEpoxyController<MutableList<Movie>>() {

    override fun buildModels(movies: MutableList<Movie>) {
        movies.forEach { movie ->
            ListMovieBindingModel_()
                    .id(movie.id)
                    .movie(movie)
                    .onBind { _, view, _ ->
                        val ratingBar = view.dataBinding.root.findViewById<RatingBar>(R.id.movieRb)
                        ratingBar.applyTheme()
                    }
                    .onClickListener { _ ->
                        this.moviesListener.onMovieClicked(movie)
                    }
                    .addTo(this)
        }

        LoaderBindingModel_()
                .id("loader")
                .onBind { _, _, _ ->
                    if (moviesListener.hasMoreToLoad()) {
                        moviesListener.fetchNextPage()
                    }
                }
                .addIf(moviesListener.hasMoreToLoad(), this)
    }


    interface MoviesListener {
        fun hasMoreToLoad(): Boolean

        fun fetchNextPage()

        fun onMovieClicked(movie: Movie)
    }
}