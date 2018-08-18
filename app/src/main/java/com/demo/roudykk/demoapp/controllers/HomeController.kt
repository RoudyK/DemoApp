package com.demo.roudykk.demoapp.controllers

import android.widget.RatingBar
import com.airbnb.epoxy.TypedEpoxyController
import com.demo.roudykk.demoapp.ListMovieBindingModel_
import com.demo.roudykk.demoapp.MovieFooterBindingModel_
import com.demo.roudykk.demoapp.MovieHeaderBindingModel_
import com.demo.roudykk.demoapp.R
import com.demo.roudykk.demoapp.extensions.applyTheme
import com.roudykk.presentation.model.MovieGroupView
import com.roudykk.presentation.model.MovieView
import javax.inject.Inject

class HomeController @Inject constructor() : TypedEpoxyController<MovieGroupView>() {

    var listener: Listener? = null

    interface Listener {
        fun onLoadMoreMovies(movieGroup: MovieGroupView)

        fun onMovieClicked(movie: MovieView)
    }

    override fun buildModels(movieGroup: MovieGroupView?) {
        movieGroup?.movies?.let {
            it.forEachIndexed { index, movie ->
                if (index == 0) {
                    MovieHeaderBindingModel_()
                            .id(movie.id)
                            .movie(movie)
                            .onClickListener { _ ->
                                this.listener?.onMovieClicked(movie)
                            }
                            .onBind { _, view, _ ->
                                val ratingBar = view.dataBinding.root.findViewById<RatingBar>(R.id.movieRb)
                                ratingBar.applyTheme()
                            }
                            .addTo(this)
                } else {
                    ListMovieBindingModel_()
                            .id(movie.id)
                            .movie(movie)
                            .onClickListener { _ ->
                                this.listener?.onMovieClicked(movie)
                            }
                            .onBind { _, view, _ ->
                                val ratingBar = view.dataBinding.root.findViewById<RatingBar>(R.id.movieRb)
                                ratingBar.applyTheme()
                            }
                            .addTo(this)
                }
            }

            MovieFooterBindingModel_()
                    .id("footer")
                    .onClickListener { _ ->
                        this.listener?.onLoadMoreMovies(movieGroup)
                    }
                    .addTo(this)
        }
    }

}