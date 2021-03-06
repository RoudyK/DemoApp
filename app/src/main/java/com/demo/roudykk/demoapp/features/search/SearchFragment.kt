package com.demo.roudykk.demoapp.features.search

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.demo.roudykk.demoapp.R
import com.demo.roudykk.demoapp.analytics.Analytics
import com.demo.roudykk.demoapp.extensions.addOverScroll
import com.demo.roudykk.demoapp.extensions.parentAppBar
import com.demo.roudykk.demoapp.extensions.viewModel
import com.demo.roudykk.demoapp.extensions.withAppBar
import com.demo.roudykk.demoapp.features.movie.MovieFragmentDirections
import com.demo.roudykk.demoapp.features.movies.MoviesController
import com.demo.roudykk.demoapp.ui.fragment.BaseFragment
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

    private val searchViewModel: SearchViewModel  by viewModel()

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
