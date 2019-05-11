package com.demo.roudykk.demoapp.ui.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.demo.roudykk.demoapp.R
import com.demo.roudykk.demoapp.analytics.Analytics
import com.demo.roudykk.demoapp.analytics.consts.Source
import com.demo.roudykk.demoapp.controllers.HomeController
import com.demo.roudykk.demoapp.injection.ViewModelFactory
import com.demo.roudykk.demoapp.ui.activity.MovieActivity
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

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private var homeViewModel: HomeViewModel? = null
    private lateinit var moviesPagerAdapter: MoviesPagerAdapter

    override val supportsFabAction: Boolean = true
    override val fabAction: () -> Unit = { findNavController().navigate(R.id.action_search) }
    override val fabAlignmentMode: Int = BottomAppBar.FAB_ALIGNMENT_MODE_CENTER

    override fun onAttach(context: Context?) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        this.initViewModel()
        this.initViewPager()

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

    private fun initViewPager() {
        this.moviesPagerAdapter = MoviesPagerAdapter(this.childFragmentManager)
        this.viewPager.adapter = this.moviesPagerAdapter
        this.tabLayout.setupWithViewPager(this.viewPager)
    }

    override fun onChanged(resource: Resource<List<MovieGroupView>>?) {
        when (resource?.status) {
            ResourceState.LOADING -> {
                this.tabLayout.visibility = View.GONE
                this.loadingView.visibility = View.VISIBLE
                this.errorView.visibility = View.GONE
                this.moviesPagerAdapter.updateMovies(listOf())
            }
            ResourceState.SUCCESS -> {
                this.tabLayout.visibility = View.VISIBLE
                this.loadingView.visibility = View.GONE
                this.errorView.visibility = View.GONE
                this.moviesPagerAdapter.updateMovies(resource.data)
            }
            ResourceState.ERROR -> {
                this.tabLayout.visibility = View.GONE
                this.loadingView.visibility = View.GONE
                this.errorView.visibility = View.VISIBLE
                this.moviesPagerAdapter.updateMovies(listOf())
            }
        }
    }

    override fun onLoadMoreMovies(movieGroup: MovieGroupView) {
        MoviesActivity.launch(context!!, movieGroup)
    }

    override fun onMovieClicked(movie: MovieView) {
        MovieActivity.launch(context!!, movie, Source.SOURCE_HOME)
    }

    inner class MoviesPagerAdapter(fragmentManager: FragmentManager)
        : FragmentStatePagerAdapter(fragmentManager) {

        private var movieGroups = mutableListOf<MovieGroupView>()
        private val movieGroupFragments = mutableListOf<MovieGroupFragment>()

        fun updateMovies(movieGroups: List<MovieGroupView>?) {
            movieGroups?.let {
                this.movieGroupFragments.clear()
                this.movieGroups = movieGroups.toMutableList()
                this.movieGroups.forEach { movieGroup ->
                    this.movieGroupFragments.add(MovieGroupFragment.newInstance(movieGroup))
                }
                this.notifyDataSetChanged()
            }
        }

        override fun getItem(position: Int): Fragment {
            return this.movieGroupFragments[position]
        }

        override fun getCount(): Int {
            return this.movieGroupFragments.size
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return this.movieGroups[position].title
        }
    }
}
