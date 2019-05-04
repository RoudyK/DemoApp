package com.demo.roudykk.demoapp.controllers

import android.content.Context
import android.graphics.Color
import androidx.core.content.ContextCompat
import android.util.TypedValue
import com.airbnb.epoxy.Carousel
import com.airbnb.epoxy.CarouselModel_
import com.airbnb.epoxy.OnModelBoundListener
import com.airbnb.epoxy.TypedEpoxyController
import com.demo.roudykk.demoapp.*
import com.demo.roudykk.demoapp.controllers.models.IndicatorCarouselModel_
import com.pierfrancescosoffritti.androidyoutubeplayer.player.YouTubePlayerView
import com.pierfrancescosoffritti.androidyoutubeplayer.player.listeners.AbstractYouTubePlayerListener
import com.roudykk.presentation.model.MovieView
import com.roudykk.presentation.model.PersonView
import com.roudykk.presentation.model.ReviewView
import com.roudykk.presentation.model.SpokenLanguageView
import me.everything.android.ui.overscroll.OverScrollDecoratorHelper
import java.text.DecimalFormat
import javax.inject.Inject


class MovieController @Inject constructor() : TypedEpoxyController<MovieView>() {
    lateinit var context: Context
    var listener: Listener? = null

    private val onModelBoundListener =
            OnModelBoundListener<CarouselModel_, Carousel>
            { _, view, _ -> OverScrollDecoratorHelper.setUpOverScroll(view, OverScrollDecoratorHelper.ORIENTATION_HORIZONTAL) }

    override fun buildModels(movie: MovieView?) {
        movie?.let {
            this.buildVideos(it)
            this.buildCast(it)
            this.buildProductionCompanies(it)
            this.buildMetrics(it)
            this.buildReviews(it)
        }
    }

    private fun buildProductionCompanies(movie: MovieView) {
        val productionModels = mutableListOf<ProductionCompanyBindingModel_>()
        movie.productionCompanies?.forEach { company ->
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

    private fun buildVideos(movie: MovieView) {
        val videoItems = mutableListOf<VideoBindingModel_>()

        movie.videos?.forEach { video ->
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

    private fun buildMetrics(movie: MovieView) {
        MetricBindingModel_()
                .id("release_date")
                .title(context.getString(R.string.release_date))
                .value(movie.releaseDate)
                .addTo(this)

        val budget = if (movie.budget == 0f) {
            context.getString(R.string.not_set)
        } else {
            DecimalFormat("#,###").format(movie.budget)
        }

        val revenue = if (movie.revenue == 0f) {
            context.getString(R.string.not_set)
        } else {
            DecimalFormat("#,###").format(movie.revenue)
        }

        MetricBindingModel_()
                .id("popularity")
                .title(context.getString(R.string.popularity))
                .value(movie.popularity.toString())
                .addTo(this)

        MetricBindingModel_()
                .id("languages")
                .title(context.getString(R.string.languages))
                .value(SpokenLanguageView.toReadableString(movie.spokenLanguages))
                .addTo(this)

        MetricBindingModel_()
                .id("budget")
                .title(context.getString(R.string.budget))
                .value(budget)
                .addTo(this)

        MetricBindingModel_()
                .id("revenue")
                .title(context.getString(R.string.revenue))
                .value(revenue)
                .addTo(this)
    }

    private fun buildCast(movie: MovieView) {
        val castModels = mutableListOf<CastBindingModel_>()

        movie.cast?.forEach { person ->
            castModels.add(CastBindingModel_()
                    .id("$person.id $person.cast_id")
                    .person(person)
                    .onClickListener { _ ->
                        this.listener?.onCastClicked(person)
                    })
        }

        CarouselModel_()
                .id("cast_carousel")
                .models(castModels)
                .padding(Carousel.Padding(0, 0))
                .onBind { _, view, _ ->
                    OverScrollDecoratorHelper.setUpOverScroll(view, OverScrollDecoratorHelper.ORIENTATION_HORIZONTAL)
                    val typedValue = TypedValue()
                    context.theme.resolveAttribute(R.attr.highlightColor, typedValue, true)
                    view.setBackgroundColor(ContextCompat.getColor(context, typedValue.resourceId))
                }
                .addIf(castModels.size > 0, this)

        DividerBindingModel_()
                .id("cast_divider")
                .addIf(castModels.size > 0, this)
    }

    private fun buildReviews(movie: MovieView) {
        TitleBindingModel_()
                .id("reviews_title")
                .title(context.getString(R.string.reviews))
                .addIf({
                    movie.reviews != null
                            && movie.reviews!!.isNotEmpty()
                }, this)

        movie.reviews?.forEach { review ->
            ReviewBindingModel_()
                    .id(review.id)
                    .review(review)
                    .onClickListener { _ ->
                        this.listener?.onReadFullReviewClicked(review)
                    }
                    .addTo(this)
        }
    }

    interface Listener {

        fun onReadFullReviewClicked(review: ReviewView)

        fun onCastClicked(person: PersonView)
    }

}