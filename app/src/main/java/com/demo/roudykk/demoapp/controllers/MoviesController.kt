package com.demo.roudykk.demoapp.controllers

import com.airbnb.epoxy.paging.PagingEpoxyController
import com.demo.roudykk.demoapp.ListMovieBindingModel_
import com.demo.roudykk.demoapp.api.model.Movie

class MoviesController : PagingEpoxyController<Movie>() {

    override fun buildModels(movies: MutableList<Movie>) {
        movies.forEach { movie ->
            ListMovieBindingModel_()
                    .id(movie.id)
                    .movie(movie)
                    .addTo(this)
        }
    }

}