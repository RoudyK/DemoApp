package com.demo.roudykk.demoapp.ui.activity


import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import butterknife.ButterKnife
import butterknife.OnClick
import com.demo.roudykk.demoapp.R
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

class MainActivity : BaseActivity(), HomeController.Listener {
    private val moviesRequests: MutableList<MoviesRequest> = mutableListOf()

    private var homeController: HomeController? = null
    private var disposable: Disposable? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ButterKnife.bind(this)
        setSupportActionBar(toolbar)

        initRv()
        loadMovies()
    }

    private fun initRv() {
        this.homeRv.layoutManager = LinearLayoutManager(this)
        this.homeRv.addOverScroll()
        this.homeRv.itemAnimator = DefaultItemAnimator()
        this.homeController = HomeController(this)
        this.homeRv.setController(homeController!!)
        this.homeRv.withAppBar(this.appBarLayout)
    }

    @OnClick(R.id.searchIv)
    fun openSearch() {
        SearchActivity.launch(this)
    }

    @OnClick(R.id.watchLaterIv)
    fun openWatchLater() {
        WatchListActivity.launch(this)
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
                }, { throwable ->
                    Log.d("ERROR-HOME", throwable.toString())
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
        Log.d("EXECTUOR", executor.toString())
        MoviesActivity.launch(this, executor)
    }

    override fun onMovieClicked(movie: Movie) {
        MovieActivity.launch(this, movie)
    }

}
