package com.demo.roudykk.demoapp.controllers

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.airbnb.epoxy.CarouselModel_
import com.airbnb.epoxy.TypedEpoxyController
import com.demo.roudykk.demoapp.R
import com.demo.roudykk.demoapp.VideoBindingModel_
import com.demo.roudykk.demoapp.api.model.Movie
import com.demo.roudykk.demoapp.ui.fragment.YoutubeVideoFragment

class MovieController(private var context: Context) : TypedEpoxyController<Movie>() {

    override fun buildModels(movie: Movie?) {
        this.buildVideos(movie)
    }

    private fun buildVideos(movie: Movie?) {
        val videoItems = mutableListOf<VideoBindingModel_>()

        movie?.videos?.results?.forEach { video ->
            videoItems.add(VideoBindingModel_()
                    .id(video.id)
                    .onBind { _, _, _ ->
                        val fragmentManager = if (context is AppCompatActivity) {
                            (context as AppCompatActivity).supportFragmentManager
                        } else {
                            (context as Fragment).childFragmentManager
                        }

                        fragmentManager
                                .beginTransaction()
                                .replace(R.id.container, YoutubeVideoFragment.newInstance(video.key))
                                .commitAllowingStateLoss()
                    })
        }

        CarouselModel_()
                .id("videos_carousel")
                .models(videoItems)
                .addIf(videoItems.size > 0, this)
    }

}