package com.demo.roudykk.demoapp.controllers

import android.widget.RatingBar
import com.airbnb.epoxy.TypedEpoxyController
import com.demo.roudykk.demoapp.ListMovieBindingModel_
import com.demo.roudykk.demoapp.LoaderBindingModel_
import com.demo.roudykk.demoapp.R
import com.demo.roudykk.demoapp.extensions.applyTheme
import com.roudykk.presentation.model.MovieView
import javax.inject.Inject

class MoviesController @Inject constructor() : TypedEpoxyController<MutableList<MovieView>>() {
    var moviesListener: MoviesListener? = null

    override fun buildModels(movies: MutableList<MovieView>?) {
        movies?.forEach { movie ->
            ListMovieBindingModel_()
                    .id(movie.id)
                    .movie(movie)
                    .onBind { _, view, _ ->
                        val ratingBar = view.dataBinding.root.findViewById<RatingBar>(R.id.movieRb)
                        ratingBar.applyTheme()
                    }
                    .onClickListener { _ ->
                        this.moviesListener?.onMovieClicked(movie)
                    }
                    .addTo(this)
        }

        LoaderBindingModel_()
                .id("loader")
                .onBind { _, _, _ ->
                    if (moviesListener != null && moviesListener!!.hasMoreToLoad()) {
                        moviesListener?.fetchNextPage()
                    }
                }
                .addIf(moviesListener != null && moviesListener!!.hasMoreToLoad(), this)
    }


    interface MoviesListener {
        fun hasMoreToLoad(): Boolean

        fun fetchNextPage()

        fun onMovieClicked(movie: MovieView)
    }
}