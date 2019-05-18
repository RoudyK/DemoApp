package com.demo.roudykk.demoapp.controllers

import com.airbnb.epoxy.TypedEpoxyController
import com.demo.roudykk.demoapp.SavedMovieBindingModel_
import com.roudykk.presentation.model.MovieView
import javax.inject.Inject

class WatchListController @Inject constructor() : TypedEpoxyController<List<MovieView>>() {

    var savedMoviesListener: SavedMoviesListener? = null

    override fun buildModels(movies: List<MovieView>?) {
        movies?.forEach { movie ->
            SavedMovieBindingModel_()
                    .id(movie.id)
                    .movie(movie)
                    .onClickListener { _ ->
                        this.savedMoviesListener?.onMovieClicked(movie)
                    }
                    .onDeleteClickListener { _ ->
                        this.savedMoviesListener?.onDeleteMovieClicked(movie)
                    }
                    .addTo(this)
        }
    }

    interface SavedMoviesListener {
        fun onMovieClicked(movie: MovieView)

        fun onDeleteMovieClicked(movie: MovieView)
    }
}