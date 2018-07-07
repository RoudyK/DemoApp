package com.demo.roudykk.demoapp.controllers

import android.content.Context
import android.support.v7.widget.SnapHelper
import android.view.View
import android.widget.ImageView
import android.widget.RatingBar
import com.airbnb.epoxy.*
import com.demo.roudykk.demoapp.HeaderBindingModel_
import com.demo.roudykk.demoapp.MovieBindingModel_
import com.demo.roudykk.demoapp.MovieFooterBindingModel_
import com.demo.roudykk.demoapp.R
import com.demo.roudykk.demoapp.api.model.Movie
import com.demo.roudykk.demoapp.api.model.MoviesResult
import com.demo.roudykk.demoapp.controllers.helper.StartSnapHelper
import com.demo.roudykk.demoapp.extensions.applyTheme
import me.everything.android.ui.overscroll.OverScrollDecoratorHelper
import me.everything.android.ui.overscroll.OverScrollDecoratorHelper.ORIENTATION_HORIZONTAL


class HomeController(val listener: Listener) : TypedEpoxyController<List<MoviesResult>>() {

    interface Listener {
        fun onLoadMoreMovies(moviesResult: MoviesResult)

        fun onMovieClicked(movie: Movie, movieIv: ImageView)
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

    override fun buildModels(moviesResults: List<MoviesResult>?) {
        moviesResults?.forEach { moviesResult ->
            HeaderBindingModel_()
                    .id(moviesResult.title)
                    .title(moviesResult.title)
                    .addTo(this)

            val moviesModels: MutableList<DataBindingEpoxyModel> = mutableListOf()
            moviesResult.results.forEach { movie ->
                moviesModels.add(MovieBindingModel_()
                        .id(movie.id)
                        .movie(movie)
                        .onBind { _, view, _ ->
                            val ratingBar = view.dataBinding.root.findViewById<RatingBar>(R.id.movieRb)
                            ratingBar.applyTheme()
                        }
                        .onClickListener { view ->
                            this.listener.onMovieClicked(movie, view.findViewById(R.id.movieIv))
                        })
            }

            moviesModels.add(MovieFooterBindingModel_()
                    .id(moviesResult.title + " footer")
                    .onClickListener(View.OnClickListener { listener.onLoadMoreMovies(moviesResult) }))

            CarouselModel_()
                    .id(moviesResult.title + " carousel")
                    .models(moviesModels)
                    .onBind(this.onModelBoundListener)
                    .addTo(this)
        }
    }

}