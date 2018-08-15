package com.demo.roudykk.demoapp.ui.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.view.inputmethod.EditorInfo
import com.demo.roudykk.demoapp.R
import com.demo.roudykk.demoapp.analytics.Analytics
import com.demo.roudykk.demoapp.controllers.MoviesController
import com.demo.roudykk.demoapp.extensions.addOverScroll
import com.demo.roudykk.demoapp.extensions.withAppBar
import com.demo.roudykk.demoapp.injection.ViewModelFactory
import com.jakewharton.rxbinding2.widget.RxTextView
import com.roudykk.presentation.model.MovieView
import kotlinx.android.synthetic.main.activity_search.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class SearchActivity : BaseActivity(), MoviesController.MoviesListener {

    @Inject
    lateinit var moviesController: MoviesController

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        this.initRv()
        this.initSearchEt()
    }

    private fun initRv() {
        this.moviesRv.layoutManager = LinearLayoutManager(this)
        this.moviesRv.addOverScroll()
        this.moviesRv.itemAnimator = DefaultItemAnimator()
//        this.moviesController = MoviesController(this)
        this.moviesRv.setController(this.moviesController)
        this.moviesRv.withAppBar(appBarLayout)
    }

    private fun initSearchEt() {
        this.searchEt.imeOptions = EditorInfo.IME_ACTION_SEARCH
        RxTextView.textChanges(this.searchEt)
                .skipInitialValue()
                .debounce(200, TimeUnit.MILLISECONDS)
                .map { query ->
                    if (query.toString().isEmpty()) {
                        this.moviesController.setData(ArrayList())
                    } else {
                        Analytics.getInstance(this)?.userSearched(query.toString())
//                        Api.searchApi().searchMovie(query.toString())
//                                .initThreads()
//                                .subscribe({ movieResult ->
////                                    this.moviesController.setData(movieResult.results)
//                                }, { throwable ->
//                                    Log.d("Search", throwable.toString())
//                                    Toast.makeText(this, getString(R.string.failed_load_movie), Toast.LENGTH_SHORT).show()
//                                })
                    }
                }
                .subscribe()
    }

    override fun hasMoreToLoad(): Boolean {
        return false
    }

    override fun fetchNextPage() {
        //DO NOTHING
    }

    override fun onMovieClicked(movie: MovieView) {
//        MovieActivity.launch(this, movie, Source.SOURCE_SEARCH)
    }

    companion object {

        fun launch(context: Context) {
            Analytics.getInstance(context)?.userOpenedSearch()
            context.startActivity(Intent(context, SearchActivity::class.java))
        }
    }
}
