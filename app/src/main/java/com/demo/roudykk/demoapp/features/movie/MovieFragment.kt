package com.demo.roudykk.demoapp.features.movie

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.TypedValue
import android.view.ContextThemeWrapper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.demo.roudykk.demoapp.R
import com.demo.roudykk.demoapp.analytics.Analytics
import com.demo.roudykk.demoapp.db.PreferenceRepo
import com.demo.roudykk.demoapp.extensions.addOverScroll
import com.demo.roudykk.demoapp.extensions.parentAppBar
import com.demo.roudykk.demoapp.extensions.viewModel
import com.demo.roudykk.demoapp.extensions.withAppBar
import com.demo.roudykk.demoapp.features.profile.ProfileFragment
import com.demo.roudykk.demoapp.ui.fragment.BaseFragment
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.snackbar.Snackbar
import com.roudykk.presentation.model.MovieView
import com.roudykk.presentation.model.PersonView
import com.roudykk.presentation.model.ReviewView
import com.roudykk.presentation.state.Resource
import com.roudykk.presentation.state.ResourceState
import com.roudykk.presentation.viewmodel.MovieViewModel
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_movie.*
import saschpe.android.customtabs.CustomTabsHelper
import saschpe.android.customtabs.WebViewFallback
import java.util.*
import javax.inject.Inject


class MovieFragment : BaseFragment(), MovieController.Listener, Observer<Resource<MovieView>> {
    override fun supportsFabAction(): Boolean = true
    override fun fabIconRes(): Int? = R.drawable.ic_add
    override fun fabAlignmentMode(): Int = BottomAppBar.FAB_ALIGNMENT_MODE_END

    @Inject
    lateinit var movieController: MovieController


    private lateinit var movie: MovieView
    private val movieViewModel: MovieViewModel by viewModel()
    private var snackBar: Snackbar? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        AndroidSupportInjection.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_movie, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        this.initViewModel()
        this.initWindow()
        this.initRv()

        arguments?.let {
            MovieFragmentArgs.fromBundle(it).let { args ->
                this.movie = args.movie
                this.populate(this.movie)
                this.movieViewModel.fetchMovie(this.movie.id)
            }
        }
    }

    override fun fabAction(): () -> Unit = {
        AlertDialog.Builder(context!!)
                .setTitle(getString(R.string.add_movie))
                .setMessage(getString(R.string.add_movie_confirmation))
                .setPositiveButton(getString(R.string.ok).toUpperCase(Locale.getDefault())) { _, _ ->
                    this.movieViewModel.addMovieWatchList(this.movie)
                    Toast.makeText(context, getString(R.string.movie_added), Toast.LENGTH_SHORT).show()
                    Analytics.getInstance(context!!).userAddedMovieWatchList(movie.id)
                }
                .setNegativeButton(getString(R.string.cancel)) { _, _ ->
                    //DO NOTHING
                }
                .create()
                .show()
    }

    private fun initViewModel() {
        this.movieViewModel.getMovie().observe(this, this)
    }

    private fun initRv() {
        this.movieRv.layoutManager = LinearLayoutManager(context)
        this.movieRv.itemAnimator = DefaultItemAnimator()
        this.movieRv.addOverScroll()
        this.movieController.listener = this
        this.movieController.context = context!!
        this.movieRv.setController(this.movieController)
    }

    override fun onResume() {
        super.onResume()
        this.movieRv.withAppBar(parentAppBar())
    }

    override fun onChanged(resource: Resource<MovieView>?) {
        when (resource?.status) {
            ResourceState.LOADING -> {
            }
            ResourceState.SUCCESS -> {
                if (resource.data != null) {
                    this.movie = resource.data!!
                    this.movieController.setData(this.movie, true)
                }
            }
            ResourceState.ERROR -> {
                this.showSnackBar()
            }
        }
    }

    private fun initWindow() {
        val typedValue = TypedValue()
        context?.theme?.resolveAttribute(R.attr.contentBackgroundColor, typedValue, true)
    }

    override fun onCastClicked(person: PersonView) {
        val personFragment = ProfileFragment.newInstance(person)
        personFragment.show(childFragmentManager, personFragment.tag)
    }

    override fun onReadFullReviewClicked(review: ReviewView) {
        val customTabsIntent = CustomTabsIntent.Builder()
                .addDefaultShareMenuItem()
                .setToolbarColor(ContextCompat.getColor(context!!, R.color.colorPrimary))
                .setShowTitle(true)
                .build()

        CustomTabsHelper.addKeepAliveExtra(context, customTabsIntent.intent)

        CustomTabsHelper.openCustomTab(context, customTabsIntent,
                Uri.parse(review.url),
                WebViewFallback())
    }

    private fun populate(movie: MovieView) {
        (activity as AppCompatActivity?)?.supportActionBar?.title = movie.title
        this.movieController.setData(movie, false)
    }

    private fun showSnackBar() {
        this.snackBar = Snackbar.make(movieRv, getString(R.string.failed_load_movie), Snackbar.LENGTH_INDEFINITE)
        this.snackBar?.setAction(getString(R.string.retry).toUpperCase(Locale.getDefault())) {
            this.movieViewModel.fetchMovie(this.movie.id)
        }
        this.snackBar?.show()
    }

    override fun onHeaderBound() {

    }
}
