package com.demo.roudykk.demoapp.ui.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.drawable.LayerDrawable
import android.os.Bundle
import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.content.ContextCompat
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import com.demo.roudykk.demoapp.R
import com.demo.roudykk.demoapp.api.model.Movie
import com.demo.roudykk.demoapp.images.AppImageLoader
import kotlinx.android.synthetic.main.header_movie.*


class MovieActivity : BaseActivity() {
    private lateinit var movie: Movie

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        collapsingLayout.isTitleEnabled = false
        val stars = movieRb.progressDrawable as LayerDrawable
        stars.getDrawable(2).setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP)
        stars.getDrawable(1).setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP)
        window.statusBarColor = Color.BLACK;
        Log.d("MOVIE", intent.extras.toString())
        if (intent != null && intent.hasExtra(MOVIE)) {
            this.movie = intent.getParcelableExtra(MOVIE)
            this.populate(this.movie)
        }
    }

    private fun populate(movie: Movie) {
        AppImageLoader.loadImage(this, movie.getImageUrl(), this.movieIv)
        this.title = movie.title
        this.movieRb.rating = movie.vote_average / 2
        this.reviewsTv.text = getString(R.string.x_reviews, movie.vote_count.toString())
        this.overviewTv.text = movie.overview
    }

    companion object {
        const val MOVIE = "MOVIE"

        fun launch(context: Context, movie: Movie, movieIv: ImageView) {
            val intent = Intent(context, MovieActivity::class.java)
            intent.putExtra(MOVIE, movie)

            val options = ActivityOptionsCompat
                    .makeSceneTransitionAnimation(context as Activity, movieIv, context.getString(R.string.movie_image))
            context.startActivity(intent, options.toBundle())
        }
    }
}
