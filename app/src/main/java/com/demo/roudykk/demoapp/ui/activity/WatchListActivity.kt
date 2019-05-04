package com.demo.roudykk.demoapp.ui.activity

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.demo.roudykk.demoapp.R
import com.demo.roudykk.demoapp.analytics.Analytics
import com.demo.roudykk.demoapp.analytics.consts.Source
import com.demo.roudykk.demoapp.controllers.SavedMoviesController
import com.demo.roudykk.demoapp.extensions.addOverScroll
import com.demo.roudykk.demoapp.extensions.withAppBar
import com.demo.roudykk.demoapp.injection.ViewModelFactory
import com.roudykk.presentation.model.MovieView
import com.roudykk.presentation.state.ResourceState
import com.roudykk.presentation.viewmodel.WatchListViewModel
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_watch_list.*
import javax.inject.Inject

class WatchListActivity : BaseActivity(), SavedMoviesController.SavedMoviesListener {

    @Inject
    lateinit var savedMoviesController: SavedMoviesController

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var watchListViewModel: WatchListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_watch_list)
        AndroidInjection.inject(this)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        title = getString(R.string.watch_list)

        this.initViewModel()
        this.initRv()
        this.initViewModel()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_watch_later, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.action_delete ->
                AlertDialog.Builder(this)
                        .setTitle(getString(R.string.delete_movies))
                        .setMessage(getString(R.string.delete_all_movies_confirmation))
                        .setPositiveButton(getString(R.string.ok).toUpperCase()) { _, _ ->
                            if (this.savedMoviesController.currentData != null) {
                                Analytics.getInstance(this).userDeletedAllMoviesWatchList(this.savedMoviesController.currentData!!.size)
                            }
                            this.watchListViewModel.clearMovieWatchList()
                        }
                        .setNegativeButton(getString(R.string.cancel)) { _, _ ->
                            //DO NOTHING
                        }
                        .create()
                        .show()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initRv() {
        this.moviesRv.layoutManager = LinearLayoutManager(this)
        this.moviesRv.itemAnimator = DefaultItemAnimator()
        this.moviesRv.addOverScroll()
        this.moviesRv.withAppBar(appBarLayout)
        this.savedMoviesController.savedMoviesListener = this
        this.moviesRv.setController(this.savedMoviesController)
    }

    private fun initViewModel() {
        this.watchListViewModel = ViewModelProviders
                .of(this, viewModelFactory)
                .get(WatchListViewModel::class.java)

        this.watchListViewModel.getMovies().observe(this, Observer { resource ->
            when (resource?.status) {
                ResourceState.SUCCESS -> {
                    val movies = resource.data
                    if (movies != null) {
                        if (movies.isNotEmpty()) {
                            emptyView.visibility = View.GONE
                        } else {
                            emptyView.visibility = View.VISIBLE
                        }
                    } else {
                        emptyView.visibility = View.VISIBLE
                    }
                    this.savedMoviesController.setData(movies)
                }
                ResourceState.ERROR -> {
                    Toast.makeText(this, getString(R.string.failed_update_watchlist),
                            Toast.LENGTH_SHORT).show()
                }
                else -> {
                    //DO NOTHING
                }
            }
        })
        this.watchListViewModel.fetchMovies()
    }

    override fun onMovieClicked(movie: MovieView) {
        MovieActivity.launch(this, movie, Source.SOURCE_WATCH_LIST)
    }

    override fun onDeleteMovieClicked(movie: MovieView) {
        AlertDialog.Builder(this)
                .setTitle(getString(R.string.delete_movie))
                .setMessage(getString(R.string.delete_movie_confirmation))
                .setPositiveButton(getString(R.string.ok).toUpperCase()) { _, _ ->
                    this.watchListViewModel.removeMovieWatchList(movie.id)
                    Analytics.getInstance(this).userDeletedMovieWatchList(movie.id)
                }
                .setNegativeButton(getString(R.string.cancel)) { _, _ ->
                    //DO NOTHING
                }
                .create()
                .show()
    }

    companion object {

        fun launch(context: Context) {
            Analytics.getInstance(context).userOpenedWatchList()
            context.startActivity(Intent(context, WatchListActivity::class.java))
        }
    }
}
