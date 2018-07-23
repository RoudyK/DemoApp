package com.demo.roudykk.demoapp.ui.activity


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
import com.demo.roudykk.demoapp.analytics.consts.Source
import com.demo.roudykk.demoapp.api.models.Movie
import com.demo.roudykk.demoapp.api.models.MoviesResult
import com.demo.roudykk.demoapp.api.requests.*
import com.demo.roudykk.demoapp.controllers.HomeController
import com.demo.roudykk.demoapp.extensions.addOverScroll
import com.demo.roudykk.demoapp.extensions.initThreads
import com.demo.roudykk.demoapp.extensions.withAppBar
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Function4
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*


class MainActivity : BaseActivity(), HomeController.Listener {
    private val moviesRequests: MutableList<MoviesRequest> = mutableListOf()
    private var homeController: HomeController? = null
    private var disposable: Disposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ButterKnife.bind(this)

        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            val menuDrawable = ContextCompat.getDrawable(this@MainActivity, R.drawable.ic_menu)
            menuDrawable?.setTint(ContextCompat.getColor(this@MainActivity, R.color.colorAccent))
            setHomeAsUpIndicator(R.drawable.ic_menu)
            title = getString(R.string.movies)
        }

        initRv()
        loadMovies()

        Analytics.getInstance(this)?.userOpenedHome()
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

    private fun initRv() {
        this.homeRv.layoutManager = LinearLayoutManager(this)
        this.homeRv.addOverScroll()
        this.homeRv.itemAnimator = DefaultItemAnimator()
        this.homeController = HomeController(this)
        this.homeRv.setController(homeController!!)
        this.homeRv.withAppBar(this.appBarLayout)
    }

    @OnClick(R.id.reload)
    fun loadMovies() {
        this.loadingView.visibility = View.VISIBLE
        this.errorView.visibility = View.GONE
        this.disposable = Observable.zip(
                this.load(HighestRatedRequest()),
                this.load(MostPopularRequest()),
                this.load(MostPopularKidsRequest()),
                this.load(MostPopularYearRequest()),
                Function4 { _: MoviesResult, _: MoviesResult,
                            _: MoviesResult, _: MoviesResult ->
                })
                .initThreads()
                .subscribe({
                    viewContent()
                }, {
                    viewError()
                })
    }

    override fun onDestroy() {
        this.disposable?.dispose()
        super.onDestroy()
    }

    private fun load(moviesRequest: MoviesRequest): Observable<MoviesResult> {
        return moviesRequest.getMovies(1)
                .initThreads()
                .doOnNext { result ->
                    moviesRequest.moviesResult = result
                    this.moviesRequests.add(moviesRequest)
                }
    }

    private fun viewContent() {
        this.loadingView.visibility = View.GONE
        this.homeController?.setData(this.moviesRequests)
    }

    private fun viewError() {
        this.loadingView.visibility = View.GONE
        this.errorView.visibility = View.VISIBLE
    }

    override fun onLoadMoreMovies(executor: MoviesRequest) {
        MoviesActivity.launch(this, executor)
    }

    override fun onMovieClicked(movie: Movie) {
        MovieActivity.launch(this, movie, Source.SOURCE_HOME)
    }

}
