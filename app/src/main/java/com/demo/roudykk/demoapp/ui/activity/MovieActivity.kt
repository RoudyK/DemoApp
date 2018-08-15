package com.demo.roudykk.demoapp.ui.activity

import android.app.AlertDialog
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.customtabs.CustomTabsIntent
import android.support.design.widget.Snackbar
import android.support.v4.content.ContextCompat
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.util.TypedValue
import android.view.View
import android.widget.Toast
import com.beloo.widget.chipslayoutmanager.ChipsLayoutManager
import com.demo.roudykk.demoapp.GenreBindingModel_
import com.demo.roudykk.demoapp.R
import com.demo.roudykk.demoapp.analytics.Analytics
import com.demo.roudykk.demoapp.analytics.consts.Source
import com.demo.roudykk.demoapp.controllers.MovieController
import com.demo.roudykk.demoapp.extensions.applyTheme
import com.demo.roudykk.demoapp.extensions.withAppBar
import com.demo.roudykk.demoapp.extensions.withModels
import com.demo.roudykk.demoapp.images.AppImageLoader
import com.demo.roudykk.demoapp.injection.ViewModelFactory
import com.demo.roudykk.demoapp.ui.fragment.PersonDetailsFragment
import com.roudykk.presentation.model.MovieView
import com.roudykk.presentation.model.PersonView
import com.roudykk.presentation.model.ReviewView
import com.roudykk.presentation.state.Resource
import com.roudykk.presentation.state.ResourceState
import com.roudykk.presentation.viewmodel.MovieViewModel
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_movie.*
import kotlinx.android.synthetic.main.header_movie.*
import saschpe.android.customtabs.CustomTabsHelper
import saschpe.android.customtabs.WebViewFallback
import javax.inject.Inject


class MovieActivity : BaseActivity(), MovieController.Listener, Observer<Resource<MovieView>> {

    @Inject
    lateinit var movieController: MovieController

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var movie: MovieView
    private lateinit var movieViewModel: MovieViewModel
    private var snackBar: Snackbar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie)
        AndroidInjection.inject(this)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        this.initViewModel()
        this.initWindow()
        this.initRv()

        if (intent != null && intent.hasExtra(MOVIE)) {
            this.movie = intent.getParcelableExtra(MOVIE)
            this.populate(this.movie)
            this.movieViewModel.fetchMovie(this.movie.id)
        }

        this.readMore.setOnClickListener {
            this.appBarLayout.setExpanded(false, true)
            Analytics.getInstance(this)?.userClickedMovieReadMore()
        }

        this.watchLater.setOnClickListener {
            AlertDialog.Builder(this)
                    .setTitle(getString(R.string.add_movie))
                    .setMessage(getString(R.string.add_movie_confirmation))
                    .setPositiveButton(getString(R.string.ok).toUpperCase()) { _, _ ->
                        this.movieViewModel.addMovieWatchList(this.movie)
                        Toast.makeText(this, getString(R.string.movie_added), Toast.LENGTH_SHORT).show()
                        Analytics.getInstance(this)?.userAddedMovieWatchList(movie.id)
                    }
                    .setNegativeButton(getString(R.string.cancel)) { _, _ ->
                        //DO NOTHING
                    }
                    .create()
                    .show()
        }
    }

    private fun initViewModel() {
        this.movieViewModel = ViewModelProviders
                .of(this, this.viewModelFactory)
                .get(MovieViewModel::class.java)

        this.movieViewModel.getMovie().observe(this, this)
    }

    private fun initRv() {
        this.movieRv.layoutManager = LinearLayoutManager(this)
        this.movieRv.itemAnimator = DefaultItemAnimator()
        this.movieRv.withAppBar(this.appBarLayout)
        this.movieController.listener = this
        this.movieController.context = this
        this.movieRv.setController(this.movieController)

        val chipsLayoutManager = ChipsLayoutManager.newBuilder(this).build()
        this.genresRv.layoutManager = chipsLayoutManager
        this.genresRv.itemAnimator = DefaultItemAnimator()
    }

    override fun onChanged(resource: Resource<MovieView>?) {
        when (resource?.status) {
            ResourceState.LOADING -> {
                this.progressBar.visibility = View.VISIBLE
                this.snackBar?.dismiss()
            }
            ResourceState.SUCCESS -> {
                this.progressBar.visibility = View.GONE
                this.movie = resource.data as MovieView
                this.movieController.setData(this.movie)
                this.populateGenres()
            }
            ResourceState.ERROR -> {
                this.progressBar.visibility = View.GONE
                Toast.makeText(this, resource.message, Toast.LENGTH_LONG).show()
                this.showSnackBar()
            }
        }
    }

    private fun populateGenres() {
        this.genresRv.withModels {
            movie.genres?.forEach { genre ->
                GenreBindingModel_()
                        .id(genre.id)
                        .genre(genre)
                        .addTo(this)
            }
        }
    }

    private fun initWindow() {
        movieRb.applyTheme()
        val typedValue = TypedValue()
        theme.resolveAttribute(R.attr.backgroundColor, typedValue, true)
    }

    override fun onCastClicked(person: PersonView) {
        val personFragment = PersonDetailsFragment.newInstance(person)
        personFragment.show(supportFragmentManager, personFragment.tag)
    }

    override fun onReadFullReviewClicked(review: ReviewView) {
        val customTabsIntent = CustomTabsIntent.Builder()
                .addDefaultShareMenuItem()
                .setToolbarColor(ContextCompat.getColor(this, R.color.colorPrimary))
                .setShowTitle(true)
                .build()

        CustomTabsHelper.addKeepAliveExtra(this, customTabsIntent.intent)

        CustomTabsHelper.openCustomTab(this, customTabsIntent,
                Uri.parse(review.url),
                WebViewFallback())
    }

    private fun populate(movie: MovieView) {
        AppImageLoader.loadImage(this, movie.getImageUrl(), this.movieIv)
        this.title = movie.title
        this.movieRb.rating = movie.voteAverage / 2
        this.reviewsTv.text = getString(R.string.x_reviews, movie.voteCount.toString())
        this.overviewTv.text = movie.overview
    }

    private fun showSnackBar() {
        this.snackBar = Snackbar.make(rootLayout, getString(R.string.failed_load_movie), Snackbar.LENGTH_INDEFINITE)
        this.snackBar?.setAction(getString(R.string.retry).toUpperCase()) {
            this.movieViewModel.fetchMovie(this.movie.id)
        }
        this.snackBar?.show()
    }

    companion object {
        const val MOVIE = "MOVIE"

        fun launch(context: Context, movie: MovieView, source: Source) {
            Analytics.getInstance(context)?.userOpenedMovie(movie.id, source)
            val intent = Intent(context, MovieActivity::class.java)
            intent.putExtra(MOVIE, movie)
            context.startActivity(intent)
        }
    }
}
