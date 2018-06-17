package com.demo.roudykk.demoapp.controller

import com.airbnb.epoxy.CarouselModel_
import com.airbnb.epoxy.DataBindingEpoxyModel
import com.airbnb.epoxy.TypedEpoxyController
import com.demo.roudykk.demoapp.HeaderBindingModel_
import com.demo.roudykk.demoapp.MovieBindingModel_
import com.demo.roudykk.demoapp.MovieFooterBindingModel_
import com.demo.roudykk.demoapp.api.model.MoviesResult

class HomeController : TypedEpoxyController<List<MoviesResult>>() {

    override fun buildModels(moviesResults: List<MoviesResult>?) {
        moviesResults?.forEach { moviesResults ->
            HeaderBindingModel_()
                    .id(moviesResults.title)
                    .title(moviesResults.title)
                    .addTo(this)

            val moviesModels: MutableList<DataBindingEpoxyModel> = mutableListOf()
            moviesResults.results.forEach { movie ->
                moviesModels.add(MovieBindingModel_()
                        .id(movie.id)
                        .movie(movie))
            }

            moviesModels.add(MovieFooterBindingModel_()
                    .id(moviesResults.title + " footer"))

            CarouselModel_()
                    .id(moviesResults.title + " carousel")
                    .models(moviesModels)
                    .addTo(this)
        }
    }

}