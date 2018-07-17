package com.demo.roudykk.demoapp.ui.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.widget.Toast
import butterknife.ButterKnife
import com.demo.roudykk.demoapp.R
import com.demo.roudykk.demoapp.analytics.Analytics
import com.demo.roudykk.demoapp.analytics.consts.Source
import com.demo.roudykk.demoapp.api.models.Movie
import com.demo.roudykk.demoapp.api.requests.MoviesRequest
import com.demo.roudykk.demoapp.controllers.MoviesController
import com.demo.roudykk.demoapp.extensions.addOverScroll
import com.demo.roudykk.demoapp.extensions.initThreads
import com.demo.roudykk.demoapp.extensions.withAppBar
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_movies.*

class MoviesActivity : BaseActivity(), MoviesController.MoviesListener {

    private var moviesController: MoviesController? = null
    private var moviesRequest: MoviesRequest? = null
    private var disposable: Disposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ButterKnife.bind(this)
        setContentView(R.layout.activity_movies)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        if (intent != null && intent.hasExtra(API_EXECUTOR)) {
            this.moviesRequest = intent.getParcelableExtra(API_EXECUTOR)
            Log.d("API-EXECUTOR", this.moviesRequest.toString())
            this.title = moviesRequest?.title
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
        this.moviesController?.setData(this.moviesRequest?.moviesResult?.results)
    }

    override fun hasMoreToLoad(): Boolean {
        return this.moviesRequest != null &&
                this.moviesRequest!!.moviesResult.page < this.moviesRequest!!.moviesResult.total_pages
    }

    override fun fetchNextPage() {
        this.moviesRequest!!.moviesResult.page++
        this.disposable = this.moviesRequest?.getMovies(this.moviesRequest!!.moviesResult.page)
                ?.initThreads()
                ?.subscribe({
                    it.results.forEach { movie ->
                        if (!this.moviesRequest!!.moviesResult.results.contains(movie)) {
                            this.moviesRequest?.moviesResult?.results?.add(movie)
                        }
                        this.moviesRequest?.moviesResult?.page = it.page
                        this.moviesRequest?.moviesResult?.total_pages = it.total_pages
                    }
                    this.moviesController?.setData(this.moviesRequest?.moviesResult?.results)
                }, {
                    Toast.makeText(this, "Failed to load", Toast.LENGTH_LONG).show()
                })
    }

    override fun onDestroy() {
        this.disposable?.dispose()
        super.onDestroy()
    }

    override fun onMovieClicked(movie: Movie) {
        MovieActivity.launch(this, movie, Source.SOURCE_MORE_MOVIES)
    }

    companion object {
        private const val API_EXECUTOR = "API_EXECUTOR"

        fun launch(context: Context, moviesRequest: MoviesRequest) {
            Analytics.getInstance(context)?.userOpenedMoreMovies(moviesRequest.title)
            val intent = Intent(context, MoviesActivity::class.java)
            intent.putExtra(API_EXECUTOR, moviesRequest)
            context.startActivity(intent)
        }
    }
}