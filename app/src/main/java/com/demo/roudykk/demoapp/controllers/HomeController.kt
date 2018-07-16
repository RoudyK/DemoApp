package com.demo.roudykk.demoapp.controllers

import android.content.Context
import android.support.v7.widget.SnapHelper
import android.view.View
import android.widget.RatingBar
import com.airbnb.epoxy.*
import com.demo.roudykk.demoapp.HeaderBindingModel_
import com.demo.roudykk.demoapp.MovieBindingModel_
import com.demo.roudykk.demoapp.MovieFooterBindingModel_
import com.demo.roudykk.demoapp.R
import com.demo.roudykk.demoapp.api.requests.MoviesRequest
import com.demo.roudykk.demoapp.api.models.Movie
import com.demo.roudykk.demoapp.controllers.helpers.StartSnapHelper
import com.demo.roudykk.demoapp.extensions.applyTheme
import me.everything.android.ui.overscroll.OverScrollDecoratorHelper
import me.everything.android.ui.overscroll.OverScrollDecoratorHelper.ORIENTATION_HORIZONTAL


class HomeController(val listener: Listener) : TypedEpoxyController<List<MoviesRequest>>() {

    interface Listener {
        fun onLoadMoreMovies(executor: MoviesRequest)

        fun onMovieClicked(movie: Movie)
    }

    private val onModelBoundListener =
            OnModelBoundListener<CarouselModel_, Carousel>
            { _, view, _ -> OverScrollDecoratorHelper.setUpOverScroll(view, ORIENTATION_HORIZONTAL) }

    init {
        Carousel.setDefaultGlobalSnapHelperFactory(object : Carousel.SnapHelperFactory() {
            override fun buildSnapHelper(context: Context?): SnapHelper {
                return StartSnapHelper()
            }
        })
    }

    override fun buildModels(moviesRequests: List<MoviesRequest>?) {
        moviesRequests?.forEach { executor ->
            HeaderBindingModel_()
                    .id(executor.title)
                    .title(executor.title)
                    .addTo(this)

            val moviesModels: MutableList<DataBindingEpoxyModel> = mutableListOf()
            executor.moviesResult.results.forEach { movie ->
                moviesModels.add(MovieBindingModel_()
                        .id(movie.id)
                        .movie(movie)
                        .onBind { _, view, _ ->
                            val ratingBar = view.dataBinding.root.findViewById<RatingBar>(R.id.movieRb)
                            ratingBar.applyTheme()
                        }
                        .onClickListener { _ ->
                            this.listener.onMovieClicked(movie)
                        })
            }

            moviesModels.add(MovieFooterBindingModel_()
                    .id(executor.title + " footer")
                    .onClickListener(View.OnClickListener { listener.onLoadMoreMovies(executor) }))

            CarouselModel_()
                    .id(executor.title + " carousel")
                    .models(moviesModels)
                    .onBind(this.onModelBoundListener)
                    .addTo(this)
        }
    }

}