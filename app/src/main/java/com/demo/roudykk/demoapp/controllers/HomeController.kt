package com.demo.roudykk.demoapp.controllers

import android.view.View
import android.widget.RatingBar
import com.airbnb.epoxy.Carousel
import com.airbnb.epoxy.CarouselModel_
import com.airbnb.epoxy.EpoxyModel
import com.airbnb.epoxy.TypedEpoxyController
import com.demo.roudykk.demoapp.HeaderBindingModel_
import com.demo.roudykk.demoapp.MovieBindingModel_
import com.demo.roudykk.demoapp.MovieHeaderBindingModel_
import com.demo.roudykk.demoapp.R
import com.demo.roudykk.demoapp.extensions.applyTheme
import com.roudykk.presentation.model.MovieGroupView
import com.roudykk.presentation.model.MovieView
import javax.inject.Inject

class HomeController @Inject constructor() : TypedEpoxyController<List<MovieGroupView>>() {

    var listener: Listener? = null

    interface Listener {
        fun onLoadMoreMovies(movieGroup: MovieGroupView)
        fun onMovieClicked(movie: MovieView, view: View)
    }

    override fun buildModels(movieGroups: List<MovieGroupView>?) {
        movieGroups?.forEach { movieGroup ->
            HeaderBindingModel_()
                    .id(movieGroup.title)
                    .title(movieGroup.title)
                    .onActionListener(View.OnClickListener { listener?.onLoadMoreMovies(movieGroup) })
                    .addTo(this)

            val models = mutableListOf<EpoxyModel<*>>()

            movieGroup.movies.let {
                it.forEachIndexed { index, movie ->
                    if (index == 0) {
                        models.add(MovieHeaderBindingModel_()
                                .id(movie.id)
                                .movie(movie)
                                .onClickListener { view ->
                                    this.listener?.onMovieClicked(movie, view.findViewById(R.id.movieIv))
                                }
                                .onBind { _, view, _ ->
                                    val ratingBar = view.dataBinding.root.findViewById<RatingBar>(R.id.movieRb)
                                    ratingBar.applyTheme()
                                })
                    } else {
                        models.add(MovieBindingModel_()
                                .id(movie.id)
                                .movie(movie)
                                .onClickListener { view ->
                                    this.listener?.onMovieClicked(movie, view.findViewById(R.id.movieIv))
                                }
                                .onBind { _, view, _ ->
                                    val ratingBar = view.dataBinding.root.findViewById<RatingBar>(R.id.movieRb)
                                    ratingBar.applyTheme()
                                })
                    }
                }

                CarouselModel_()
                        .id("carousel_${movieGroup.title}")
                        .models(models)
                        .onBind { _, view, _ ->
                            view.isNestedScrollingEnabled = false
                        }
                        .padding(Carousel.Padding.resource(R.dimen.spacing_default, R.dimen.spacing_small, R.dimen.empty, R.dimen.empty, R.dimen.spacing_default))
                        .addTo(this)
            }
        }
    }
}