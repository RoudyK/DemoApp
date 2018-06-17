package com.demo.roudykk.demoapp.controller

import android.content.Context
import android.support.v7.widget.SnapHelper
import com.airbnb.epoxy.*
import com.demo.roudykk.demoapp.HeaderBindingModel_
import com.demo.roudykk.demoapp.MovieBindingModel_
import com.demo.roudykk.demoapp.MovieFooterBindingModel_
import com.demo.roudykk.demoapp.api.model.MoviesResult
import com.demo.roudykk.demoapp.controller.helper.StartSnapHelper
import me.everything.android.ui.overscroll.OverScrollDecoratorHelper
import me.everything.android.ui.overscroll.OverScrollDecoratorHelper.ORIENTATION_HORIZONTAL


class HomeController : TypedEpoxyController<List<MoviesResult>>() {
    val onModelBoundListener =
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
                    .onBind(this.onModelBoundListener)
                    .addTo(this)
        }
    }

}