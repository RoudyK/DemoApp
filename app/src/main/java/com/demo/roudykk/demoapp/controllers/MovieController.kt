package com.demo.roudykk.demoapp.controllers

import android.content.Context
import android.graphics.Color
import com.airbnb.epoxy.Carousel
import com.airbnb.epoxy.TypedEpoxyController
import com.demo.roudykk.demoapp.R
import com.demo.roudykk.demoapp.VideoBindingModel_
import com.demo.roudykk.demoapp.api.model.Movie
import com.demo.roudykk.demoapp.controllers.model.IndicatorCarouselModel_
import com.pierfrancescosoffritti.androidyoutubeplayer.player.YouTubePlayerView
import com.pierfrancescosoffritti.androidyoutubeplayer.player.listeners.AbstractYouTubePlayerListener


class MovieController(private var context: Context) : TypedEpoxyController<Movie>() {

    override fun buildModels(movie: Movie?) {
        this.buildVideos(movie)
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
                            initializedYouTubePlayer.addListener(
                                    object : AbstractYouTubePlayerListener() {
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

}