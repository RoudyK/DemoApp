package com.demo.roudykk.demoapp.ui.activity

import android.arch.paging.PagedList
import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import butterknife.ButterKnife
import com.demo.roudykk.demoapp.R
import com.demo.roudykk.demoapp.api.model.Movie
import com.demo.roudykk.demoapp.api.model.MoviesResult
import com.demo.roudykk.demoapp.controllers.MoviesController
import com.demo.roudykk.demoapp.db.MoviesDataSource
import com.demo.roudykk.demoapp.extensions.addOverScroll
import com.demo.roudykk.demoapp.extensions.withAppBar
import kotlinx.android.synthetic.main.activity_movies.*
import java.util.concurrent.Executor

class MoviesActivity : BaseActivity() {

    private var moviesController: MoviesController? = null
    private var pagedMovies: PagedList<Movie>? = null
    private var moviesResult: MoviesResult? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ButterKnife.bind(this)
        setContentView(R.layout.activity_movies)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        if (intent != null && intent.hasExtra(MOVIES_RESULT)) {
            moviesResult = intent.getParcelableExtra(MOVIES_RESULT)
            title = moviesResult?.title
        }

        initRv()
    }

    private fun initRv() {
        this.pagedMovies = PagedList.Builder<Int, Movie>(
                MoviesDataSource(moviesResult),
                PagedList.Config.Builder().run {
                    setEnablePlaceholders(false)
                    setPageSize(20)
                    build()
                })
                .run {
                    setNotifyExecutor(UiThreadExecutor)
                    setFetchExecutor(AsyncTask.THREAD_POOL_EXECUTOR)
                    build()
                }

        this.moviesRv.layoutManager = LinearLayoutManager(this)
        this.moviesRv.addOverScroll()
        this.moviesRv.itemAnimator = DefaultItemAnimator()
        this.moviesController = MoviesController()
        this.moviesRv.setController(this.moviesController!!)
        this.moviesRv.withAppBar(appBarLayout)
        this.moviesController?.setList(this.pagedMovies)
    }

    companion object {
        private const val MOVIES_RESULT = "MOVIES_RESULT"

        fun launch(context: Context, moviesResult: MoviesResult) {
            val intent = Intent(context, MoviesActivity::class.java)
            intent.putExtra(MOVIES_RESULT, moviesResult)
            context.startActivity(intent)
        }
    }

    object UiThreadExecutor : Executor {
        private val handler = Handler(Looper.getMainLooper())

        override fun execute(command: Runnable) {
            handler.post(command)
        }
    }
}