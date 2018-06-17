package com.demo.roudykk.demoapp

import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import com.demo.roudykk.demoapp.api.executor.*
import com.demo.roudykk.demoapp.api.model.MoviesResult
import com.demo.roudykk.demoapp.controller.HomeController
import com.demo.roudykk.demoapp.util.extensions.addOverScroll
import com.demo.roudykk.demoapp.util.extensions.initThreads
import io.reactivex.Observable
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private val moviesResults: MutableList<MoviesResult> = mutableListOf()
    lateinit var homeController: HomeController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        title = ""
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
                    homeController.setData(moviesResults)
                }
                .subscribe()
    }
}
