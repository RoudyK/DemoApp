package com.demo.roudykk.demoapp.ui.activity

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.drawable.LayerDrawable
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.content.ContextCompat
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import android.widget.ImageView
import com.demo.roudykk.demoapp.R
import com.demo.roudykk.demoapp.api.Api
import com.demo.roudykk.demoapp.api.model.Movie
import com.demo.roudykk.demoapp.controllers.MovieController
import com.demo.roudykk.demoapp.extensions.addOverScroll
import com.demo.roudykk.demoapp.extensions.initThreads
import com.demo.roudykk.demoapp.extensions.withAppBar
import com.demo.roudykk.demoapp.images.AppImageLoader
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_movie.*
import kotlinx.android.synthetic.main.header_movie.*


class MovieActivity : BaseActivity() {
    private lateinit var movie: Movie
    private var disposable: Disposable? = null
    private var snackbar: Snackbar? = null
    private var movieController: MovieController? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        this.initWindow()
        this.initRv()

        if (intent != null && intent.hasExtra(MOVIE)) {
            this.movie = intent.getParcelableExtra(MOVIE)
            this.populate(this.movie)
            this.loadMovieDetails(this.movie.id)
        }
    }

    private fun initRv() {
        this.movieRv.layoutManager = LinearLayoutManager(this)
        this.movieRv.itemAnimator = DefaultItemAnimator()
        this.movieRv.addOverScroll()
        this.movieRv.withAppBar(this.appBarLayout)
        this.movieController = MovieController(this)
        this.movieRv.setController(this.movieController!!)
    }

    private fun loadMovieDetails(id: Int?) {
        this.progressBar.visibility = View.VISIBLE
        this.snackbar?.dismiss()
        this.disposable = Api.movieApi().getMovieDetails(id)
                .initThreads()
                .subscribe({ movie ->
                    this.progressBar.visibility = View.GONE
                    this.movie = movie
                    this.movieController?.setData(this.movie)
                    Log.d("MOVIE", this.movie.toString())
                }, {
                    this.progressBar.visibility = View.GONE
                    this.showSnackBar()
                })
    }

    private fun initWindow() {
        collapsingLayout.isTitleEnabled = false
        val stars = movieRb.progressDrawable as LayerDrawable
        stars.getDrawable(2).setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP)
        stars.getDrawable(1).setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP)
        window.statusBarColor = ContextCompat.getColor(this, R.color.colorDim)
    }

    private fun populate(movie: Movie) {
        AppImageLoader.loadImage(this, movie.getImageUrl(), this.movieIv)
        this.title = movie.title
        this.movieRb.rating = movie.vote_average / 2
        this.reviewsTv.text = getString(R.string.x_reviews, movie.vote_count.toString())
        this.overviewTv.text = movie.overview
    }

    private fun showSnackBar() {
        this.snackbar = Snackbar.make(rootLayout, getString(R.string.failed_load_movie), Snackbar.LENGTH_INDEFINITE)
        this.snackbar?.setAction(getString(R.string.retry).toUpperCase()) {
            this.loadMovieDetails(this.movie.id)
        }
        this.snackbar?.show()
    }

    override fun onDestroy() {
        this.disposable?.dispose()
        super.onDestroy()
    }

    companion object {
        const val MOVIE = "MOVIE"

        fun launch(context: Context, movie: Movie, movieIv: ImageView) {
            val intent = Intent(context, MovieActivity::class.java)
            intent.putExtra(MOVIE, movie)
            context.startActivity(intent)
        }
    }
}
