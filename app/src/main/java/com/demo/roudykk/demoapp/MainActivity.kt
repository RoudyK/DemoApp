package com.demo.roudykk.demoapp

import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.demo.roudykk.demoapp.api.executor.*
import com.demo.roudykk.demoapp.api.model.MoviesResult
import com.demo.roudykk.demoapp.controller.HomeController
import com.demo.roudykk.demoapp.util.extensions.addOverScroll
import com.demo.roudykk.demoapp.util.extensions.initThreads
import io.reactivex.Observable
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private val moviesResults: MutableList<MoviesResult> = mutableListOf()
    lateinit var homeController: HomeController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        initDrawer()
        initRv()
        loadMovies()
    }

    private fun initRv() {
        postsRv.layoutManager = LinearLayoutManager(this)
        postsRv.addOverScroll()
        homeController = HomeController()
        postsRv.setController(homeController)
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

    private fun initDrawer() {
        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> return true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_camera -> {

            }
            R.id.nav_gallery -> {

            }
            R.id.nav_slideshow -> {

            }
            R.id.nav_manage -> {

            }
            R.id.nav_share -> {

            }
            R.id.nav_send -> {

            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }
}
