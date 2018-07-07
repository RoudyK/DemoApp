package com.demo.roudykk.demoapp.controllers

import android.content.Context
import android.graphics.Color
import android.support.v4.content.ContextCompat
import com.airbnb.epoxy.Carousel
import com.airbnb.epoxy.CarouselModel_
import com.airbnb.epoxy.OnModelBoundListener
import com.airbnb.epoxy.TypedEpoxyController
import com.demo.roudykk.demoapp.*
import com.demo.roudykk.demoapp.api.model.Movie
import com.demo.roudykk.demoapp.controllers.model.IndicatorCarouselModel_
import com.pierfrancescosoffritti.androidyoutubeplayer.player.YouTubePlayerView
import com.pierfrancescosoffritti.androidyoutubeplayer.player.listeners.AbstractYouTubePlayerListener
import me.everything.android.ui.overscroll.OverScrollDecoratorHelper
import java.text.DecimalFormat


class MovieController(private var context: Context) : TypedEpoxyController<Movie>() {

    private val onModelBoundListener =
            OnModelBoundListener<CarouselModel_, Carousel>
            { _, view, _ -> OverScrollDecoratorHelper.setUpOverScroll(view, OverScrollDecoratorHelper.ORIENTATION_HORIZONTAL) }

    override fun buildModels(movie: Movie?) {
        this.buildVideos(movie)
        this.buildProductionCompanies(movie)
        this.buildMetrics(movie)
        this.buildCast(movie)
        this.buildReviews(movie)
    }

    private fun buildProductionCompanies(movie: Movie?) {
        val productionModels = mutableListOf<ProductionCompanyBindingModel_>()
        movie?.production_companies?.forEach { company ->
            productionModels.add(ProductionCompanyBindingModel_()
                    .id(company.id)
                    .company(company))
        }

        CarouselModel_()
                .id("production_companies_carousel")
                .models(productionModels)
                .padding(Carousel.Padding(0, 0))
                .onBind(onModelBoundListener)
                .addIf(productionModels.size > 0, this)

        DividerBindingModel_()
                .id("production_companies_divider")
                .addIf(productionModels.size > 0, this)
    }

    private fun buildVideos(movie: Movie?) {
        val videoItems = mutableListOf<VideoBindingModel_>()

        movie?.videos?.results?.forEach { video ->
            videoItems.add(VideoBindingModel_()
                    .id(video.id)
                    .video(video)
                    .onBind { _, view, _ ->
                        val youTubePlayerView = view.dataBinding.root.findViewById<YouTubePlayerView>(R.id.youtubeView)
                        youTubePlayerView.initialize({ initializedYouTubePlayer ->
                            initializedYouTubePlayer.addListener(object : AbstractYouTubePlayerListener() {
                                override fun onReady() {
                                    initializedYouTubePlayer.cueVideo(video.key, 0f)
                                }
                            })
                        }, true)
                    })
        }

        IndicatorCarouselModel_()
                .id("videos_carousel")
                .models(videoItems)
                .padding(Carousel.Padding(0, 20, 0, 0, 0))
                .onBind { _, view, _ ->
                    view.setBackgroundColor(Color.BLACK)
                    view.setPadding(0, 0, 0, 20)
                }
                .addIf(videoItems.size > 0, this)
    }

    private fun buildMetrics(movie: Movie?) {
        MetricBindingModel_()
                .id("release_date")
                .title(context.getString(R.string.release_date))
                .value(movie?.release_date)
                .addTo(this)

        val budget = if (movie?.budget == 0f) {
            context.getString(R.string.not_set)
        } else {
            DecimalFormat("#,###").format(movie?.budget)
        }

        MetricBindingModel_()
                .id("budget")
                .title(context.getString(R.string.budget))
                .value(budget)
                .addTo(this)
    }

    private fun buildCast(movie: Movie?) {
        val castModels = mutableListOf<CastBindingModel_>()

        movie?.credits?.cast?.forEach { person ->
            castModels.add(CastBindingModel_()
                    .id(person.id)
                    .person(person))
        }

        CarouselModel_()
                .id("cast_carousel")
                .models(castModels)
                .padding(Carousel.Padding(0, 0))
                .onBind { _, view, _ ->
                    OverScrollDecoratorHelper.setUpOverScroll(view, OverScrollDecoratorHelper.ORIENTATION_HORIZONTAL)
                    view.setBackgroundColor(ContextCompat.getColor(context, R.color.colorDark))
                }
                .addIf(castModels.size > 0, this)

        DividerBindingModel_()
                .id("cast_divider")
                .addIf(castModels.size > 0, this)
    }

    private fun buildReviews(movie: Movie?) {
        TitleBindingModel_()
                .id("reviews_title")
                .title(context.getString(R.string.reviews))
                .addIf({
                    movie?.reviews != null
                            && movie.reviews?.results != null
                            && movie.reviews!!.results!!.size > 0
                }, this)

        movie?.reviews?.results?.forEach { review ->
            ReviewBindingModel_()
                    .id(review.id)
                    .review(review)
                    .addTo(this)
        }
    }

}