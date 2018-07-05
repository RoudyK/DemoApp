package com.demo.roudykk.demoapp.controllers

import android.widget.ImageView
import com.airbnb.epoxy.TypedEpoxyController
import com.demo.roudykk.demoapp.ListMovieBindingModel_
import com.demo.roudykk.demoapp.LoaderBindingModel_
import com.demo.roudykk.demoapp.R
import com.demo.roudykk.demoapp.api.model.Movie

class MoviesController(private val moviesListener: MoviesListener) : TypedEpoxyController<MutableList<Movie>>() {

    override fun buildModels(movies: MutableList<Movie>) {
        movies.forEach { movie ->
            ListMovieBindingModel_()
                    .id(movie.id)
                    .movie(movie)
                    .onClickListener { view ->
                        this.moviesListener.onMovieClicked(movie, view.findViewById(R.id.movieIv))
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

        fun onMovieClicked(movie: Movie, movieIv: ImageView)
    }
}