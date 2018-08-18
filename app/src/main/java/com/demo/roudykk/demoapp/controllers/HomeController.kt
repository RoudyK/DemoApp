package com.demo.roudykk.demoapp.controllers

import com.airbnb.epoxy.TypedEpoxyController
import com.demo.roudykk.demoapp.ListMovieBindingModel_
import com.demo.roudykk.demoapp.MovieFooterBindingModel_
import com.demo.roudykk.demoapp.MovieHeaderBindingModel_
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
                            .addTo(this)
                } else {
                    ListMovieBindingModel_()
                            .id(movie.id)
                            .movie(movie)
                            .onClickListener { _ ->
                                this.listener?.onMovieClicked(movie)
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