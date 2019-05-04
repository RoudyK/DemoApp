package com.demo.roudykk.demoapp.ui.activity

import android.graphics.PorterDuff
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import butterknife.ButterKnife
import butterknife.OnClick
import com.demo.roudykk.demoapp.R
import com.demo.roudykk.demoapp.analytics.Analytics
import com.demo.roudykk.demoapp.analytics.consts.Source
import com.demo.roudykk.demoapp.controllers.HomeController
import com.demo.roudykk.demoapp.injection.ViewModelFactory
import com.demo.roudykk.demoapp.ui.fragment.MovieGroupFragment
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
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var moviesPagerAdapter: MoviesPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ButterKnife.bind(this)
        AndroidInjection.inject(this)

        this.initViewModel()
        this.initToolbar()
        this.initDrawer()
        this.initViewPager()
        this.loadMovies()

        Analytics.getInstance(this).userOpenedHome()
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

    private fun initViewPager() {
        this.moviesPagerAdapter = MoviesPagerAdapter(this.supportFragmentManager)
        this.viewPager.adapter = this.moviesPagerAdapter
        this.tabLayout.setupWithViewPager(this.viewPager)
    }

    override fun onChanged(resource: Resource<List<MovieGroupView>>?) {
        when (resource?.status) {
            ResourceState.LOADING -> {
                this.tabLayout.visibility = View.GONE
                this.loadingView.visibility = View.VISIBLE
                this.errorView.visibility = View.GONE
                this.moviesPagerAdapter.updateMovies(listOf())
            }
            ResourceState.SUCCESS -> {
                this.tabLayout.visibility = View.VISIBLE
                this.loadingView.visibility = View.GONE
                this.errorView.visibility = View.GONE
                this.moviesPagerAdapter.updateMovies(resource.data)
            }
            ResourceState.ERROR -> {
                this.tabLayout.visibility = View.GONE
                this.loadingView.visibility = View.GONE
                this.errorView.visibility = View.VISIBLE
                this.moviesPagerAdapter.updateMovies(listOf())
            }
        }
    }

    @OnClick(R.id.reload)
    fun loadMovies() {
        this.homeViewModel.fetchMovieGroups()
    }

    override fun onLoadMoreMovies(movieGroup: MovieGroupView) {
        MoviesActivity.launch(this, movieGroup)
    }

    override fun onMovieClicked(movie: MovieView) {
        MovieActivity.launch(this, movie, Source.SOURCE_HOME)
    }

    inner class MoviesPagerAdapter(fragmentManager: FragmentManager)
        : FragmentPagerAdapter(fragmentManager) {

        private var movieGroups = mutableListOf<MovieGroupView>()
        private val movieGroupFragments = mutableListOf<MovieGroupFragment>()

        fun updateMovies(movieGroups: List<MovieGroupView>?) {
            movieGroups?.let {
                this.movieGroupFragments.clear()
                this.movieGroups = movieGroups.toMutableList()
                this.movieGroups.forEach { movieGroup ->
                    this.movieGroupFragments.add(MovieGroupFragment.newInstance(movieGroup))
                }
                this.notifyDataSetChanged()
            }
        }

        override fun getItem(position: Int): Fragment {
            return this.movieGroupFragments[position]
        }

        override fun getCount(): Int {
            return this.movieGroupFragments.size
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return this.movieGroups[position].title
        }
    }

}
