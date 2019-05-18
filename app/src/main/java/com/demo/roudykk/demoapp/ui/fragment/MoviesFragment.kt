package com.demo.roudykk.demoapp.ui.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.demo.roudykk.demoapp.R
import com.demo.roudykk.demoapp.controllers.MoviesController
import com.demo.roudykk.demoapp.extensions.addOverScroll
import com.demo.roudykk.demoapp.extensions.parentAppBar
import com.demo.roudykk.demoapp.extensions.viewModel
import com.demo.roudykk.demoapp.extensions.withAppBar
import com.demo.roudykk.demoapp.injection.ViewModelFactory
import com.roudykk.presentation.model.MovieGroupView
import com.roudykk.presentation.model.MovieView
import com.roudykk.presentation.state.Resource
import com.roudykk.presentation.state.ResourceState
import com.roudykk.presentation.viewmodel.MoviesViewModel
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_movies.*
import javax.inject.Inject

class MoviesFragment : BaseFragment(), MoviesController.MoviesListener, Observer<Resource<List<MovieView>>> {

    @Inject
    lateinit var moviesController: MoviesController

    private val moviesViewModel: MoviesViewModel  by viewModel()
    private lateinit var movieGroup: MovieGroupView

    override fun onAttach(context: Context) {
        super.onAttach(context)
        AndroidSupportInjection.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_movies, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        this.initViewModel()

        arguments?.let {
            MoviesFragmentArgs.fromBundle(it).let { args ->
                this.movieGroup = args.movieGroup
                (activity as AppCompatActivity?)?.supportActionBar?.title = this.movieGroup.title
            }
        }

        initRv()
    }

    private fun initViewModel() {
        this.moviesViewModel.getMovies().observe(this, this)
    }

    private fun initRv() {
        this.moviesRv.layoutManager = LinearLayoutManager(context)
        this.moviesRv.addOverScroll()
        this.moviesRv.itemAnimator = DefaultItemAnimator()
        this.moviesController.moviesListener = this
        this.moviesRv.setController(this.moviesController)
        this.moviesController.setData(this.movieGroup.movies)
    }

    override fun onResume() {
        super.onResume()
        this.moviesRv.withAppBar(parentAppBar())
    }

    override fun hasMoreToLoad(): Boolean {
        return this.movieGroup.page < this.movieGroup.totalPages
    }

    override fun fetchNextPage() {
        this.moviesViewModel.fetchMovies(this.movieGroup.index, ++this.movieGroup.page)
    }

    override fun onChanged(resource: Resource<List<MovieView>>?) {
        when (resource?.status) {
            ResourceState.SUCCESS -> {
                resource.data?.forEach { movie ->
                    if (!this.movieGroup.movies.contains(movie)) {
                        this.movieGroup.movies.add(movie)
                    }
                }
                this.moviesController.setData(this.movieGroup.movies)
            }
            ResourceState.ERROR -> {
                Toast.makeText(context, getString(R.string.failed_to_load_movies), Toast.LENGTH_LONG).show()
            }
            else -> {
                //NOTHING
            }
        }
    }


    override fun onMovieClicked(movie: MovieView) {
        findNavController().navigate(MovieFragmentDirections.actionMovie(movie))
    }
}