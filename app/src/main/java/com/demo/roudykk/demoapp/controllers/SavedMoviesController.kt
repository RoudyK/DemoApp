package com.demo.roudykk.demoapp.controllers

import com.airbnb.epoxy.TypedEpoxyController
import com.demo.roudykk.demoapp.SavedMovieBindingModel_
import com.demo.roudykk.demoapp.api.models.Movie

class SavedMoviesController(private val savedMoviesListener: SavedMoviesListener) : TypedEpoxyController<List<Movie>>() {

    override fun buildModels(movies: List<Movie>?) {
        movies?.forEach { movie ->
            SavedMovieBindingModel_()
                    .id(movie.id)
                    .movie(movie)
                    .onClickListener { _ ->
                        this.savedMoviesListener.onMovieClicked(movie)
                    }
                    .onDeleteClickListener { _ ->
                        this.savedMoviesListener.onDeleteMovieClicked(movie)
                    }
                    .addTo(this)
        }
    }

    interface SavedMoviesListener {
        fun onMovieClicked(movie: Movie)

        fun onDeleteMovieClicked(movie: Movie)
    }
}