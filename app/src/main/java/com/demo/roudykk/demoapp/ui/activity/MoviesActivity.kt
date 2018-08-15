package com.demo.roudykk.demoapp.ui.activity

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.widget.Toast
import butterknife.ButterKnife
import com.demo.roudykk.demoapp.R
import com.demo.roudykk.demoapp.analytics.Analytics
import com.demo.roudykk.demoapp.analytics.consts.Source
import com.demo.roudykk.demoapp.controllers.MoviesController
import com.demo.roudykk.demoapp.extensions.addOverScroll
import com.demo.roudykk.demoapp.extensions.withAppBar
import com.demo.roudykk.demoapp.injection.ViewModelFactory
import com.roudykk.presentation.model.MovieGroupView
import com.roudykk.presentation.model.MovieView
import com.roudykk.presentation.state.Resource
import com.roudykk.presentation.state.ResourceState
import com.roudykk.presentation.viewmodel.MoviesViewModel
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_movies.*
import javax.inject.Inject

class MoviesActivity : BaseActivity(), MoviesController.MoviesListener, Observer<Resource<List<MovieView>>> {

    @Inject
    lateinit var moviesController: MoviesController

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var moviesViewModel: MoviesViewModel
    private lateinit var movieGroup: MovieGroupView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ButterKnife.bind(this)
        AndroidInjection.inject(this)

        setContentView(R.layout.activity_movies)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        this.initViewModel()

        if (intent != null && intent.hasExtra(MOVIE_GROUP)) {
            this.movieGroup = intent.getParcelableExtra(MOVIE_GROUP)
            this.title = movieGroup.title
        }

        initRv()
    }

    private fun initViewModel() {
        this.moviesViewModel = ViewModelProviders
                .of(this, this.viewModelFactory)
                .get(MoviesViewModel::class.java)

        this.moviesViewModel.getMovies().observe(this, this)
    }

    private fun initRv() {
        this.moviesRv.layoutManager = LinearLayoutManager(this)
        this.moviesRv.addOverScroll()
        this.moviesRv.itemAnimator = DefaultItemAnimator()
        this.moviesController.moviesListener = this
        this.moviesRv.setController(this.moviesController)
        this.moviesRv.withAppBar(appBarLayout)
        this.moviesController.setData(this.movieGroup.movies)
    }

    override fun hasMoreToLoad(): Boolean {
        return this.movieGroup.page < this.movieGroup.totalPages
    }

    override fun fetchNextPage() {
        this.moviesViewModel.fetchMovies(this.movieGroup.index, ++this.movieGroup.page)
    }

    override fun onChanged(resource: Resource<List<MovieView>>?) {
        when (resource?.status) {
            ResourceState.SUCCESS -> {
                resource.data?.forEach { movie ->
                    if (!this.movieGroup.movies.contains(movie)) {
                        this.movieGroup.movies.add(movie)
                    }
                }
                this.moviesController.setData(this.movieGroup.movies)
            }
            ResourceState.ERROR -> {
                Toast.makeText(this, "Failed to load", Toast.LENGTH_LONG).show()
            }
            else -> {
                //NOTHING
            }
        }
    }


    override fun onMovieClicked(movie: MovieView) {
        MovieActivity.launch(this, movie, Source.SOURCE_MORE_MOVIES)
    }

    companion object {
        private const val MOVIE_GROUP = "MOVIE_GROUP"

        fun launch(context: Context, movieGroup: MovieGroupView) {
            Analytics.getInstance(context)?.userOpenedMoreMovies(movieGroup.title)
            val intent = Intent(context, MoviesActivity::class.java)
            intent.putExtra(MOVIE_GROUP, movieGroup)
            context.startActivity(intent)
        }
    }
}