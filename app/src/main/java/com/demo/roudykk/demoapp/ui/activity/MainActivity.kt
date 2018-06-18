package com.demo.roudykk.demoapp.ui.activity

import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import butterknife.ButterKnife
import butterknife.OnClick
import com.demo.roudykk.demoapp.R
import com.demo.roudykk.demoapp.api.executor.*
import com.demo.roudykk.demoapp.api.model.MoviesResult
import com.demo.roudykk.demoapp.controller.HomeController
import com.demo.roudykk.demoapp.extensions.addOverScroll
import com.demo.roudykk.demoapp.extensions.initThreads
import com.demo.roudykk.demoapp.extensions.withAppBar
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Function4
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : BaseActivity(), HomeController.Listener {
    private val moviesResults: MutableList<MoviesResult> = mutableListOf()
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

    @OnClick(R.id.reload)
    fun loadMovies() {
        this.loadingView.visibility = View.VISIBLE
        this.errorView.visibility = View.GONE
        this.disposable = Observable.zip(
                this.load(HighestRatedExecutor()),
                this.load(MostPopularExecutor()),
                this.load(MostPopularKidsExecutor()),
                this.load(MostPopularYearExecutor()),
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

    private fun load(apiExecutor: ApiExecutor): Observable<MoviesResult> {
        return apiExecutor.getMovies(1)
                .initThreads()
                .doOnNext { result ->
                    result.title = apiExecutor.title()
                    result.executor = apiExecutor
                    this.moviesResults.add(result)
                }
    }

    private fun viewContent() {
        this.loadingView.visibility = View.GONE
        this.homeController?.setData(this.moviesResults)
    }

    private fun viewError() {
        this.loadingView.visibility = View.GONE
        this.errorView.visibility = View.VISIBLE
    }

    override fun onLoadMoreMovies(moviesResult: MoviesResult) {
        MoviesActivity.launch(this, moviesResult)
    }

}