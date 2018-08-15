package com.demo.roudykk.demoapp.ui.activity

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.graphics.PorterDuff
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v4.view.GravityCompat
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.view.View
import butterknife.ButterKnife
import butterknife.OnClick
import com.demo.roudykk.demoapp.R
import com.demo.roudykk.demoapp.analytics.Analytics
import com.demo.roudykk.demoapp.controllers.HomeController
import com.demo.roudykk.demoapp.extensions.addOverScroll
import com.demo.roudykk.demoapp.extensions.withAppBar
import com.demo.roudykk.demoapp.injection.ViewModelFactory
import com.roudykk.presentation.model.MovieGroupView
import com.roudykk.presentation.model.MovieView
import com.roudykk.presentation.state.Resource
import com.roudykk.presentation.state.ResourceState
import com.roudykk.presentation.viewmodel.HomeViewModel
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import javax.inject.Inject

class MainActivity : BaseActivity(), HomeController.Listener, Observer<Resource<List<MovieGroupView>>> {

    @Inject
    lateinit var homeController: HomeController

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var homeViewModel: HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ButterKnife.bind(this)
        AndroidInjection.inject(this)

        this.initViewModel()
        this.initToolbar()
        this.initDrawer()
        this.initRv()
        this.loadMovies()

        Analytics.getInstance(this)?.userOpenedHome()
    }

    private fun initViewModel() {
        this.homeViewModel = ViewModelProviders
                .of(this, viewModelFactory)
                .get(HomeViewModel::class.java)

        this.homeViewModel.getMovieGroups().observe(this, this)
    }

    private fun initToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            val menuDrawable = ContextCompat.getDrawable(this@MainActivity, R.drawable.ic_menu)
            menuDrawable?.setTint(ContextCompat.getColor(this@MainActivity, R.color.colorAccent))
            setHomeAsUpIndicator(R.drawable.ic_menu)
            title = getString(R.string.movies)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_content_home, menu)
        val drawable = menu?.findItem(R.id.nav_search)?.icon
        drawable?.mutate()
        drawable?.setColorFilter(ContextCompat.getColor(this, R.color.colorAccent), PorterDuff.Mode.SRC_ATOP)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            android.R.id.home -> {
                drawerLayout.openDrawer(GravityCompat.START)
                true
            }
            R.id.nav_search -> {
                SearchActivity.launch(this)
                true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    private fun initDrawer() {
        this.navigationView.setNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_watchlist -> {
                    WatchListActivity.launch(this)
                    true
                }
                R.id.nav_settings -> {
                    SettingsActivity.launch(this)
                    true
                }
                else -> super.onOptionsItemSelected(item)
            }
        }
    }

    private fun initRv() {
        this.homeRv.layoutManager = LinearLayoutManager(this)
        this.homeRv.addOverScroll()
        this.homeRv.itemAnimator = DefaultItemAnimator()
        this.homeController.listener = this
        this.homeRv.setController(homeController)
        this.homeRv.withAppBar(this.appBarLayout)
    }

    override fun onChanged(resource: Resource<List<MovieGroupView>>?) {
        when (resource?.status) {
            ResourceState.LOADING -> {
                this.loadingView.visibility = View.VISIBLE
                this.errorView.visibility = View.GONE
                this.homeController.setData(listOf())
            }
            ResourceState.SUCCESS -> {
                this.loadingView.visibility = View.GONE
                this.errorView.visibility = View.GONE
                this.homeController.setData(resource.data)
            }
            ResourceState.ERROR -> {
                this.loadingView.visibility = View.GONE
                this.errorView.visibility = View.VISIBLE
                this.homeController.setData(listOf())
            }
        }
    }

    @OnClick(R.id.reload)
    fun loadMovies() {
        this.homeViewModel.fetchMovieGroups()
    }

    override fun onLoadMoreMovies(movieGroup: MovieGroupView) {
//        MoviesActivity.launch(this, movieGroup)
    }

    override fun onMovieClicked(movie: MovieView) {
//        MovieActivity.launch(this, movie, Source.SOURCE_HOME)
    }

}
