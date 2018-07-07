package com.demo.roudykk.demoapp.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.View
import com.demo.roudykk.demoapp.R
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayerSupportFragment

class YoutubeVideoFragment : YouTubePlayerSupportFragment(), YouTubePlayer.OnInitializedListener {

    override fun onInitializationSuccess(provider: YouTubePlayer.Provider, youTubePlayer: YouTubePlayer, b: Boolean) {
        youTubePlayer.setPlayerStyle(YouTubePlayer.PlayerStyle.DEFAULT)

        val videoKey: String? = arguments?.getString(VIDEO_KEY)
        youTubePlayer.loadVideo(videoKey)
    }

    override fun onInitializationFailure(provider: YouTubePlayer.Provider, youTubeInitializationResult: YouTubeInitializationResult) {

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        this.initialize(getString(R.string.youtube_key), this)
    }

    companion object {
        private const val VIDEO_KEY = "VIDEO_KEY"

        fun newInstance(videoKey: String): YoutubeVideoFragment {
            val youtubeVideoFragment = YoutubeVideoFragment()
            val bundle = Bundle()
            bundle.putString(VIDEO_KEY, videoKey)
            youtubeVideoFragment.arguments = bundle
            return youtubeVideoFragment
        }
    }

}
