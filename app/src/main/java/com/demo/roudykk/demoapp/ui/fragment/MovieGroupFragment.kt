package com.demo.roudykk.demoapp.ui.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.demo.roudykk.demoapp.R
import com.demo.roudykk.demoapp.analytics.consts.Source
import com.demo.roudykk.demoapp.controllers.HomeController
import com.demo.roudykk.demoapp.extensions.addOverScroll
import com.demo.roudykk.demoapp.ui.activity.MovieActivity
import com.demo.roudykk.demoapp.ui.activity.MoviesActivity
import com.roudykk.presentation.model.MovieGroupView
import com.roudykk.presentation.model.MovieView
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_movie_group.*
import javax.inject.Inject

class MovieGroupFragment : Fragment(), HomeController.Listener {

    @Inject
    lateinit var homeController: HomeController

    override fun onAttach(context: Context?) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_movie_group, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        this.initRV()
        if (arguments != null) {
            val movieGroupView = arguments?.getParcelable<MovieGroupView>(MOVIE_GROUP)
            this.homeController.setData(movieGroupView)
        }
    }

    private fun initRV() {
        this.moviesRv.layoutManager = LinearLayoutManager(context)
        this.moviesRv.itemAnimator = DefaultItemAnimator()
        this.homeController.listener = this
        this.moviesRv.setController(this.homeController)
    }

    override fun onLoadMoreMovies(movieGroup: MovieGroupView) {
        activity?.let { MoviesActivity.launch(it, movieGroup) }
    }

    override fun onMovieClicked(movie: MovieView) {
        activity?.let { MovieActivity.launch(it, movie, Source.SOURCE_HOME) }
    }

    companion object {

        private const val MOVIE_GROUP = "MOVIE_GROUP"

        fun newInstance(movieGroup: MovieGroupView): MovieGroupFragment {
            val movieGroupFragment = MovieGroupFragment()
            val args = Bundle()
            args.putParcelable(MOVIE_GROUP, movieGroup)
            movieGroupFragment.arguments = args
            return movieGroupFragment
        }
    }
}