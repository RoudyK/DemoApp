package com.demo.roudykk.demoapp.ui.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.demo.roudykk.demoapp.R
import com.demo.roudykk.demoapp.analytics.Analytics
import com.demo.roudykk.demoapp.controllers.HomeController
import com.demo.roudykk.demoapp.injection.ViewModelFactory
import com.demo.roudykk.demoapp.ui.activity.MoviesActivity
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
    lateinit var viewModelFactory: ViewModelFactory

    @Inject
    lateinit var homeController: HomeController

    private var homeViewModel: HomeViewModel? = null

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        movieRv.layoutManager = LinearLayoutManager(context)
        homeController.listener = this
        movieRv.setController(homeController)

        this.initViewModel()

        reload.setOnClickListener { this.homeViewModel?.fetchMovieGroups() }

        Analytics.getInstance(context!!).userOpenedHome()
    }

    private fun initViewModel() {
        this.homeViewModel = ViewModelProviders
                .of(this, viewModelFactory)
                .get(HomeViewModel::class.java)

        this.homeViewModel?.getMovieGroups()?.observe(this, this)
        this.homeViewModel?.fetchMovieGroups()
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
        MoviesActivity.launch(context!!, movieGroup)
    }

    override fun onMovieClicked(movie: MovieView, view: View) {
        findNavController().navigate(MovieFragmentDirections.actionMovie(movie))
    }

    override fun onSaveInstanceState(outState: Bundle) {
        homeController.onSaveInstanceState(outState)
        super.onSaveInstanceState(outState)
    }
}
