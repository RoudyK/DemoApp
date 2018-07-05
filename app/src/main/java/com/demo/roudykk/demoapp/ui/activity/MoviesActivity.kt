package com.demo.roudykk.demoapp.ui.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.widget.ImageView
import android.widget.Toast
import butterknife.ButterKnife
import com.demo.roudykk.demoapp.R
import com.demo.roudykk.demoapp.api.model.Movie
import com.demo.roudykk.demoapp.api.model.MoviesResult
import com.demo.roudykk.demoapp.controllers.MoviesController
import com.demo.roudykk.demoapp.extensions.addOverScroll
import com.demo.roudykk.demoapp.extensions.initThreads
import com.demo.roudykk.demoapp.extensions.withAppBar
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_movies.*

class MoviesActivity : BaseActivity(), MoviesController.MoviesListener {

    private var moviesController: MoviesController? = null
    private var moviesResult: MoviesResult? = null
    private var hasMoreToLoad = true
    private var disposable: Disposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ButterKnife.bind(this)
        setContentView(R.layout.activity_movies)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        if (intent != null && intent.hasExtra(MOVIES_RESULT)) {
            this.moviesResult = intent.getParcelableExtra(MOVIES_RESULT)
            this.title = moviesResult?.title
        }

        initRv()
    }

    private fun initRv() {
        this.moviesRv.layoutManager = LinearLayoutManager(this)
        this.moviesRv.addOverScroll()
        this.moviesRv.itemAnimator = DefaultItemAnimator()
        this.moviesController = MoviesController(this)
        this.moviesRv.setController(this.moviesController!!)
        this.moviesRv.withAppBar(appBarLayout)
        this.moviesController?.setData(this.moviesResult!!.results)
    }

    override fun hasMoreToLoad(): Boolean {
        return this.hasMoreToLoad
    }

    override fun fetchNextPage() {
        this.moviesResult!!.page++
        this.disposable = this.moviesResult?.executor?.getMovies(this.moviesResult!!.page)
                ?.initThreads()
                ?.subscribe({
                    it.results.forEach { movie ->
                        if (!this.moviesResult!!.results.contains(movie)) {
                            this.moviesResult?.results?.add(movie)
                        }
                    }
                    this.moviesController?.setData(this.moviesResult!!.results)
                }, {
                    Toast.makeText(this, "Failed to load", Toast.LENGTH_LONG).show()
                })
    }

    override fun onDestroy() {
        this.disposable?.dispose()
        super.onDestroy()
    }

    override fun onMovieClicked(movie: Movie, movieIv: ImageView) {
        MovieActivity.launch(this, movie, movieIv)
    }

    companion object {
        private const val MOVIES_RESULT = "MOVIES_RESULT"

        fun launch(context: Context, moviesResult: MoviesResult) {
            val intent = Intent(context, MoviesActivity::class.java)
            intent.putExtra(MOVIES_RESULT, moviesResult)
            context.startActivity(intent)
        }
    }
}