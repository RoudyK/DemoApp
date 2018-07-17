package com.demo.roudykk.demoapp.ui.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.widget.Toast
import com.demo.roudykk.demoapp.R
import com.demo.roudykk.demoapp.api.Api
import com.demo.roudykk.demoapp.api.models.Movie
import com.demo.roudykk.demoapp.controllers.MoviesController
import com.demo.roudykk.demoapp.extensions.addOverScroll
import com.demo.roudykk.demoapp.extensions.initThreads
import com.demo.roudykk.demoapp.extensions.withAppBar
import com.jakewharton.rxbinding2.widget.RxTextView
import kotlinx.android.synthetic.main.activity_search.*
import java.util.concurrent.TimeUnit

class SearchActivity : BaseActivity(), MoviesController.MoviesListener {
    private lateinit var moviesController: MoviesController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        this.initRv()
        this.initSearchEt()
    }

    private fun initRv() {
        this.moviesRv.layoutManager = LinearLayoutManager(this)
        this.moviesRv.addOverScroll()
        this.moviesRv.itemAnimator = DefaultItemAnimator()
        this.moviesController = MoviesController(this)
        this.moviesRv.setController(this.moviesController)
        this.moviesRv.withAppBar(appBarLayout)
    }

    private fun initSearchEt() {
        RxTextView.textChanges(this.searchEt)
                .skipInitialValue()
                .debounce(200, TimeUnit.MILLISECONDS)
                .map { query ->
                    if (query.toString().isEmpty()) {
                        this.moviesController.setData(ArrayList())
                    } else {
                        Api.searchApi().searchMovie(query.toString())
                                .initThreads()
                                .subscribe({ movieResult ->
                                    this.moviesController.setData(movieResult.results)
                                }, { throwable ->
                                    Log.d("Search", throwable.toString())
                                    Toast.makeText(this, getString(R.string.failed_load_movie), Toast.LENGTH_SHORT).show()
                                })
                    }
                }
                .subscribe()
    }

    override fun hasMoreToLoad(): Boolean {
        return false
    }

    override fun fetchNextPage() {
        //DO NOTHING
    }

    override fun onMovieClicked(movie: Movie) {
        MovieActivity.launch(this, movie)
    }

    companion object {

        fun launch(context: Context) {
            context.startActivity(Intent(context, SearchActivity::class.java))
        }
    }
}
