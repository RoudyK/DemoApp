package com.demo.roudykk.demoapp.features.home

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.demo.roudykk.demoapp.R
import com.demo.roudykk.demoapp.analytics.Analytics
import com.demo.roudykk.demoapp.extensions.*
import com.demo.roudykk.demoapp.features.movie.MovieFragmentDirections
import com.demo.roudykk.demoapp.features.movies.MoviesFragmentDirections
import com.demo.roudykk.demoapp.ui.fragment.BaseFragment
import com.google.android.material.bottomappbar.BottomAppBar
import com.roudykk.presentation.model.MovieGroupView
import com.roudykk.presentation.model.MovieView
import com.roudykk.presentation.state.Resource
import com.roudykk.presentation.state.ResourceState
import com.roudykk.presentation.viewmodel.HomeViewModel
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_home.*
import javax.inject.Inject

class HomeFragment : BaseFragment(), HomeController.Listener, Observer<Resource<List<MovieGroupView>>> {
    override val fabIconRes: Int? = R.drawable.ic_search
    override val supportsFabAction: Boolean = true
    override val fabAction: () -> Unit = { findNavController().navigate(R.id.action_search) }
    override val fabAlignmentMode: Int = BottomAppBar.FAB_ALIGNMENT_MODE_CENTER

    @Inject
    lateinit var homeController: HomeController

    private val homeViewModel: HomeViewModel by viewModel()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        AndroidSupportInjection.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        movieHomeRv.layoutManager = LinearLayoutManager(context)

        homeController.listener = this
        movieHomeRv.addOverScroll()
        movieHomeRv.setController(homeController)

        this.initViewModel()

        reload.setOnClickListener { this.homeViewModel.fetchMovieGroups() }

        Analytics.getInstance(context!!).userOpenedHome()
    }

    override fun onResume() {
        super.onResume()
        this.movieHomeRv.withAppBar(parentAppBar())
    }

    private fun initViewModel() {
        this.homeViewModel.getMovieGroups().observe(this, this)
        this.homeViewModel.fetchMovieGroups()
    }

    override fun onChanged(resource: Resource<List<MovieGroupView>>?) {
        when (resource?.status) {
            ResourceState.LOADING -> {
                this.loadingView.visibility = View.VISIBLE
                this.errorView.visibility = View.GONE
            }
            ResourceState.SUCCESS -> {
                homeController.setData(resource.data)
                this.loadingView.visibility = View.GONE
                this.errorView.visibility = View.GONE
            }
            ResourceState.ERROR -> {
                this.loadingView.visibility = View.GONE
                this.errorView.visibility = View.VISIBLE
            }
        }
    }

    override fun onLoadMoreMovies(movieGroup: MovieGroupView) {
        findNavController().navigate(MoviesFragmentDirections.actionMovieGroup(movieGroup))
    }

    override fun onMovieClicked(movie: MovieView, view: View) {
        findNavController().navigate(MovieFragmentDirections.actionMovie(movie))
    }

    override fun onSaveInstanceState(outState: Bundle) {
        homeController.onSaveInstanceState(outState)
        super.onSaveInstanceState(outState)
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        trySafe {
            homeController.onRestoreInstanceState(savedInstanceState)
        }
    }
}
