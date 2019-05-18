package com.demo.roudykk.demoapp.features.watchlist

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog.Builder
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
import com.demo.roudykk.demoapp.ui.fragment.BaseFragment
import com.google.android.material.bottomappbar.BottomAppBar
import com.roudykk.presentation.model.MovieView
import com.roudykk.presentation.state.ResourceState
import com.roudykk.presentation.viewmodel.WatchListViewModel
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_watch_list.*
import java.util.*
import javax.inject.Inject

class WatchListFragment : BaseFragment(), WatchListController.SavedMoviesListener {
    override val supportsFabAction: Boolean = true
    override val fabIconRes: Int? = R.drawable.ic_delete
    override val fabAlignmentMode: Int = BottomAppBar.FAB_ALIGNMENT_MODE_END

    @Inject
    lateinit var watchListController: WatchListController

    private val watchListViewModel: WatchListViewModel  by viewModel()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        AndroidSupportInjection.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_watch_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        this.initViewModel()
        this.initRv()
        this.initViewModel()
    }


    override val fabAction: () -> Unit = {
        Builder(context!!)
                .setTitle(getString(R.string.delete_movies))
                .setMessage(getString(R.string.delete_all_movies_confirmation))
                .setPositiveButton(getString(R.string.ok).toUpperCase(Locale.getDefault())) { _, _ ->
                    if (this.watchListController.currentData != null) {
                        Analytics.getInstance(context!!).userDeletedAllMoviesWatchList(this.watchListController.currentData!!.size)
                    }
                    this.watchListViewModel.clearMovieWatchList()
                }
                .setNegativeButton(getString(R.string.cancel)) { _, _ ->
                    //DO NOTHING
                }
                .create()
                .show()
    }

    private fun initRv() {
        this.moviesRv.layoutManager = LinearLayoutManager(context)
        this.moviesRv.itemAnimator = DefaultItemAnimator()
        this.moviesRv.addOverScroll()
        this.watchListController.savedMoviesListener = this
        this.moviesRv.setController(this.watchListController)
    }

    override fun onResume() {
        super.onResume()
        this.moviesRv.withAppBar(parentAppBar())
    }

    private fun initViewModel() {
        this.watchListViewModel.getMovies().observe(this, Observer { resource ->
            when (resource?.status) {
                ResourceState.SUCCESS -> {
                    val movies = resource.data
                    if (movies != null) {
                        if (movies.isNotEmpty()) {
                            emptyView.visibility = View.GONE
                        } else {
                            emptyView.visibility = View.VISIBLE
                        }
                    } else {
                        emptyView.visibility = View.VISIBLE
                    }
                    this.watchListController.setData(movies)
                }
                ResourceState.ERROR -> {
                    Toast.makeText(context, getString(R.string.failed_update_watchlist),
                            Toast.LENGTH_SHORT).show()
                }
                else -> {
                    //DO NOTHING
                }
            }
        })
        this.watchListViewModel.fetchMovies()
    }

    override fun onMovieClicked(movie: MovieView) {
        findNavController().navigate(MovieFragmentDirections.actionMovie(movie))
    }

    override fun onDeleteMovieClicked(movie: MovieView) {
        Builder(context!!)
                .setTitle(getString(R.string.delete_movie))
                .setMessage(getString(R.string.delete_movie_confirmation))
                .setPositiveButton(getString(R.string.ok).toUpperCase(Locale.getDefault())) { _, _ ->
                    this.watchListViewModel.removeMovieWatchList(movie.id)
                    Analytics.getInstance(context!!).userDeletedMovieWatchList(movie.id)
                }
                .setNegativeButton(getString(R.string.cancel)) { _, _ ->
                    //DO NOTHING
                }
                .create()
                .show()
    }
}
