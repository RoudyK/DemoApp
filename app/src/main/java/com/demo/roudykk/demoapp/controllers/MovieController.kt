package com.demo.roudykk.demoapp.controllers

import android.content.Context
import androidx.recyclerview.widget.DefaultItemAnimator
import com.airbnb.epoxy.Carousel
import com.airbnb.epoxy.CarouselModel_
import com.airbnb.epoxy.EpoxyRecyclerView
import com.airbnb.epoxy.Typed2EpoxyController
import com.beloo.widget.chipslayoutmanager.ChipsLayoutManager
import com.demo.roudykk.demoapp.*
import com.demo.roudykk.demoapp.controllers.models.IndicatorCarouselModel_
import com.demo.roudykk.demoapp.extensions.addOverScroll
import com.pierfrancescosoffritti.androidyoutubeplayer.player.YouTubePlayerView
import com.pierfrancescosoffritti.androidyoutubeplayer.player.listeners.AbstractYouTubePlayerListener
import com.roudykk.presentation.model.MovieView
import com.roudykk.presentation.model.PersonView
import com.roudykk.presentation.model.ReviewView
import com.roudykk.presentation.model.SpokenLanguageView
import java.text.DecimalFormat
import javax.inject.Inject


class MovieController @Inject constructor() : Typed2EpoxyController<MovieView, Boolean>() {
    lateinit var context: Context
    var listener: Listener? = null

    override fun buildModels(movie: MovieView?, fullDetails: Boolean) {
        movie?.let {
            this.buildHeader(it, fullDetails)
            if (fullDetails) {
                this.buildVideos(it)
                this.buildCast(it)
                this.buildProductionCompanies(it)
                this.buildMetrics(it)
                this.buildReviews(it)
            }
        }
    }

    private fun buildHeader(movie: MovieView, fullDetails: Boolean) {
        HeaderMovieBindingModel_()
                .id("header")
                .movie(movie)
                .onBind { _, view, _ ->
                    listener?.onHeaderBound()
                    val genresRv = view.dataBinding.root.findViewById<EpoxyRecyclerView>(R.id.genresRv)
                    genresRv.itemAnimator = DefaultItemAnimator()
                    if (fullDetails) {
                        genresRv.layoutManager = ChipsLayoutManager.newBuilder(context).build()
                        genresRv.buildModelsWith { controller ->
                            movie.genres?.forEach {
                                GenreBindingModel_()
                                        .id(it.id)
                                        .genre(it)
                                        .addTo(controller)
                            }
                        }
                    } else {
                        genresRv.clear()
                    }
                }
                .addTo(this)
    }

    private fun buildProductionCompanies(movie: MovieView) {
        movie.productionCompanies?.let {
            HeaderBindingModel_()
                    .id("prod_companies_header")
                    .title(context.getString(R.string.production_companies))
                    .hideAction(true)
                    .addTo(this)

            val productionModels = mutableListOf<ProductionCompanyBindingModel_>()
            movie.productionCompanies?.forEach { company ->
                productionModels.add(ProductionCompanyBindingModel_()
                        .id(company.id)
                        .company(company))
            }

            CarouselModel_()
                    .id("production_companies_carousel")
                    .models(productionModels)
                    .padding(Carousel.Padding.resource(R.dimen.spacing_default, R.dimen.spacing_small, R.dimen.spacing_default, R.dimen.empty, R.dimen.spacing_default))
                    .onBind { _, view, _ ->
                        view.isNestedScrollingEnabled = false
                    }
                    .addIf(productionModels.size > 0, this)
        }
    }

    private fun buildVideos(movie: MovieView) {
        movie.videos?.let {

            HeaderBindingModel_()
                    .id("trailers_header")
                    .title(context.getString(R.string.trailers))
                    .hideAction(true)
                    .addIf(it.isNotEmpty(), this)

            val videoItems = mutableListOf<VideoBindingModel_>()

            val videos = if (it.size > 3) {
                it.subList(0, 3)
            } else {
                it
            }

            videos.forEach { video ->
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
                        view.isNestedScrollingEnabled = false
                    }
                    .addIf(videoItems.size > 0, this)
        }
    }

    private fun buildMetrics(movie: MovieView) {
        HeaderBindingModel_()
                .id("metrics_header")
                .title(context.getString(R.string.metrics))
                .hideAction(true)
                .addTo(this)

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
        movie.cast?.let {

            HeaderBindingModel_()
                    .id("cast_header")
                    .title(context.getString(R.string.crew_cast))
                    .hideAction(true)
                    .addTo(this)

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
                    .padding(Carousel.Padding.resource(R.dimen.spacing_default, R.dimen.spacing_small, R.dimen.spacing_default, R.dimen.empty, R.dimen.spacing_default))
                    .onBind { _, view, _ ->
                        view.addOverScroll()
                        view.isNestedScrollingEnabled = false
                    }
                    .addIf(castModels.size > 0, this)
        }
    }

    private fun buildReviews(movie: MovieView) {
        movie.reviews?.let {
            HeaderBindingModel_()
                    .id("reviews_header")
                    .title(context.getString(R.string.reviews))
                    .hideAction(true)
                    .addIf(it.isNotEmpty(), this)

            movie.reviews?.forEach { review ->
                ReviewBindingModel_()
                        .id(review.id)
                        .review(review)
                        .onClickListener { _ ->
                            this.listener?.onReadFullReviewClicked(review)
                        }
                        .addIf(it.isNotEmpty(), this)
            }
        }
    }

    interface Listener {
        fun onReadFullReviewClicked(review: ReviewView)
        fun onCastClicked(person: PersonView)
        fun onHeaderBound()
    }
}