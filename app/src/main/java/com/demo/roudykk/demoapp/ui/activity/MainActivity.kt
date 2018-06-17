package com.demo.roudykk.demoapp.ui.activity

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import com.demo.roudykk.demoapp.R
import com.demo.roudykk.demoapp.api.executor.*
import com.demo.roudykk.demoapp.api.model.MoviesResult
import com.demo.roudykk.demoapp.controller.HomeController
import com.demo.roudykk.demoapp.extensions.addOverScroll
import com.demo.roudykk.demoapp.extensions.initThreads
import io.reactivex.Observable
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : BaseActivity() {
    private val moviesResults: MutableList<MoviesResult> = mutableListOf()
    lateinit var homeController: HomeController
    var animating = false
    var animated = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        window.statusBarColor = ContextCompat.getColor(this, R.color.colorPrimaryDark)

        initRv()
        loadMovies()
    }

    private fun initRv() {
        homeRv.layoutManager = LinearLayoutManager(this)
        homeRv.addOverScroll()
        homeRv.itemAnimator = DefaultItemAnimator()
        homeController = HomeController()
        homeRv.setController(homeController)
        homeRv.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (homeRv.canScrollVertically(-1)) {
                    appBarLayout.elevation = 6F
                } else {
                    appBarLayout.elevation = 0F
                }
            }
        })
    }

    private fun loadMovies() {
        this.load(HighestRatedExecutor(), "Highest rated")
        this.load(MostPopularExecutor(), "Most popular")
        this.load(MostPopularKidsExecutor(), "Most popular for kids")
        this.load(MostPopularYearExecutor(), "Most popular this year")
    }

    private fun load(apiExecutor: ApiExecutor, title: String) {
        apiExecutor.getMovies(1)
                .initThreads()
                .doOnError { error ->
                    Log.d("Error", error.toString())
                }
                .onErrorResumeNext(Observable.empty())
                .doOnNext { result ->
                    result.title = title
                    result.executor = apiExecutor
                    moviesResults.add(result)
                    if (!animated) {
                        crossfade()
                    }

                    if (!animating && animated) {
                        homeController.setData(moviesResults)
                    }
                }
                .subscribe()
    }

    private fun crossfade() {
        val shortDuration = resources.getInteger(android.R.integer.config_shortAnimTime)
        animating = true
        animated = true

        loadingView.animate()
                .alpha(0f)
                .setDuration(shortDuration.toLong())
                .setListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator) {
                        animating = false
                        homeController.setData(moviesResults)
                    }
                })
    }
}