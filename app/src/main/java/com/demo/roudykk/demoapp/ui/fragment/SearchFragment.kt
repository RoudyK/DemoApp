package com.demo.roudykk.demoapp.ui.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.demo.roudykk.demoapp.R
import com.demo.roudykk.demoapp.analytics.Analytics
import com.demo.roudykk.demoapp.controllers.MoviesController
import com.demo.roudykk.demoapp.extensions.addOverScroll
import com.demo.roudykk.demoapp.extensions.parentAppBar
import com.demo.roudykk.demoapp.extensions.withAppBar
import com.demo.roudykk.demoapp.injection.ViewModelFactory
import com.jakewharton.rxbinding2.widget.RxTextView
import com.roudykk.presentation.model.MovieView
import com.roudykk.presentation.state.ResourceState
import com.roudykk.presentation.viewmodel.SearchViewModel
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_search.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class SearchFragment : BaseFragment(), MoviesController.MoviesListener {

    @Inject
    lateinit var moviesController: MoviesController

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var searchViewModel: SearchViewModel

    override fun onAttach(context: Context) {
        super.onAttach(context)
        AndroidSupportInjection.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        this.initViewModel()
        this.initRv()
        this.initSearchEt()
    }

    private fun initViewModel() {
        this.searchViewModel = ViewModelProviders
                .of(this, this.viewModelFactory)
                .get(SearchViewModel::class.java)

        this.searchViewModel.getMovies().observe(this,
                Observer { resource ->
                    when (resource?.status) {
                        ResourceState.LOADING -> {
                            //DO NOTHING
                        }
                        ResourceState.SUCCESS -> {
                            this.moviesController.setData(resource.data?.toMutableList())
                        }
                        ResourceState.ERROR -> {
                            Toast.makeText(context, getString(R.string.failed_load_movie), Toast.LENGTH_SHORT).show()
                        }
                    }
                })
    }

    private fun initRv() {
        this.moviesRv.layoutManager = LinearLayoutManager(context)
        this.moviesRv.addOverScroll()
        this.moviesRv.itemAnimator = DefaultItemAnimator()
        this.moviesController.moviesListener = this
        this.moviesRv.setController(this.moviesController)
    }

    override fun onResume() {
        super.onResume()
        parentAppBar()?.elevation = 0f
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
                        Analytics.getInstance(context!!).userSearched(query.toString())
                        this.searchViewModel.fetchMovies(query.toString())
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
        findNavController().navigate(MovieFragmentDirections.actionMovie(movie))
    }
}
