package com.demo.roudykk.demoapp.ui.activity

import android.app.AlertDialog
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.demo.roudykk.demoapp.R
import com.demo.roudykk.demoapp.api.models.Movie
import com.demo.roudykk.demoapp.controllers.SavedMoviesController
import com.demo.roudykk.demoapp.db.models.MovieViewModel
import com.demo.roudykk.demoapp.extensions.addOverScroll
import com.demo.roudykk.demoapp.extensions.withAppBar
import kotlinx.android.synthetic.main.activity_watch_list.*

class WatchListActivity : BaseActivity(), SavedMoviesController.SavedMoviesListener {
    private lateinit var savedMoviesController: SavedMoviesController
    private lateinit var movieViewModel: MovieViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_watch_list)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        title = getString(R.string.watch_list)

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
                            this.movieViewModel.deleteAll()
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
        moviesRv.layoutManager = LinearLayoutManager(this)
        moviesRv.itemAnimator = DefaultItemAnimator()
        moviesRv.addOverScroll()
        moviesRv.withAppBar(appBarLayout)
        this.savedMoviesController = SavedMoviesController(this)
        moviesRv.setController(this.savedMoviesController)
    }

    private fun initViewModel() {
        this.movieViewModel = ViewModelProviders.of(this).get(MovieViewModel::class.java)

        this.movieViewModel.getAll().observe(this, Observer<List<Movie>> { movies ->
            this.savedMoviesController.setData(movies)

            if (movies != null) {
                if (movies.isNotEmpty()) {
                    emptyView.visibility = View.GONE
                } else {
                    emptyView.visibility = View.VISIBLE
                }
            } else {
                emptyView.visibility = View.VISIBLE
            }
        })
    }

    override fun onMovieClicked(movie: Movie) {
        Log.d("OPENED-MOVIE", movie.toString())
        MovieActivity.launch(this, movie)
    }

    override fun onDeleteMovieClicked(movie: Movie) {
        AlertDialog.Builder(this)
                .setTitle(getString(R.string.delete_movie))
                .setMessage(getString(R.string.delete_movie_confirmation))
                .setPositiveButton(getString(R.string.ok).toUpperCase()) { _, _ ->
                    this.movieViewModel.delete(movie.id)
                }
                .setNegativeButton(getString(R.string.cancel)) { _, _ ->
                    //DO NOTHING
                }
                .create()
                .show()
    }

    companion object {

        fun launch(context: Context) {
            context.startActivity(Intent(context, WatchListActivity::class.java))
        }
    }
}
