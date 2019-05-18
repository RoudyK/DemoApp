package com.demo.roudykk.demoapp.activities

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.swipeLeft
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.rule.ActivityTestRule
import com.demo.roudykk.demoapp.R
import com.demo.roudykk.demoapp.TestApplication
import com.demo.roudykk.demoapp.test.MoviesFactory
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.whenever
import com.roudykk.domain.model.Movie
import com.roudykk.domain.model.MovieGroup
import io.reactivex.Observable
import org.hamcrest.CoreMatchers.allOf
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import androidx.test.espresso.intent.Intents.times
import com.demo.roudykk.demoapp.MainActivity
import com.demo.roudykk.demoapp.features.movie.MovieFragment
import com.demo.roudykk.demoapp.features.movies.MoviesFragment
import com.demo.roudykk.demoapp.features.search.SearchFragment
import com.demo.roudykk.demoapp.features.watchlist.WatchListFragment


class MainActivityTest {

    @Rule
    @JvmField
    var activity = ActivityTestRule<MainActivity>(MainActivity::class.java, false, false)

    @Before
    fun setup() {
        Intents.init()
    }

    @After
    fun finish() {
        Intents.release()
    }

    @Test
    fun activityLaunches() {
        this.stubGetMovieGroups(Observable.just(listOf()))
        this.activity.launchActivity(null)
    }

    @Test
    fun activityDisplaysMovies() {
        val movieGroups = MoviesFactory.makeMovieGroups()
        this.stubGetMovieGroups(Observable.just(movieGroups))
        this.activity.launchActivity(null)

        movieGroups.forEach { movieGroup ->
            onView(withText(movieGroup.title))
                    .check(matches(isDisplayed()))

            movieGroup.movies.forEach { movie ->
                onView(withText(movie.title))
                        .check(matches(isDisplayed()))
            }

            onView(allOf(withText(R.string.view_more), isCompletelyDisplayed()))
                    .check(matches(isDisplayed()))

            onView(withId(R.id.viewPager))
                    .perform(swipeLeft())
        }
    }

    @Test
    fun activityDisplaysErrorView() {
        this.stubGetMovieGroups(Observable.error(RuntimeException()))
        this.activity.launchActivity(null)

        onView(withId(R.id.errorView))
                .check(matches(isDisplayed()))
    }

    @Test
    fun activityRecyclerItemsOpenMovie() {
        val movieGroups = MoviesFactory.makeMovieGroups()
        val movie = MoviesFactory.makeMovie()
        this.stubGetMovieDetails(Observable.just(movie))
        this.stubGetMovieGroups(Observable.just(movieGroups))
        this.activity.launchActivity(null)

        onView(allOf(hasDescendant(withText(movieGroups[0].movies[0].title)),
                withContentDescription("movieContainer")))
                .perform(click())

        intended(hasComponent(MovieFragment::class.java.name))
    }

    @Test
    fun activitySearchMenuOpensSearch() {
        val movieGroups = MoviesFactory.makeMovieGroups()
        this.stubGetMovieGroups(Observable.just(movieGroups))
        this.activity.launchActivity(null)

        onView(withId(R.id.nav_search))
                .perform(click())

        intended(hasComponent(SearchFragment::class.java.name))
    }

    @Test
    fun activityBurgerMenuOpensSideNav() {
        val movieGroups = MoviesFactory.makeMovieGroups()
        this.stubGetMovieGroups(Observable.just(movieGroups))
        this.activity.launchActivity(null)

        onView(withContentDescription(R.string.abc_action_bar_up_description))
                .perform(click())

        onView(withId(R.id.navigationView))
                .check(matches(isDisplayed()))
    }

    @Test
    fun watchListMenuOpensWatchList() {
        val movieGroups = MoviesFactory.makeMovieGroups()
        val movies = listOf(MoviesFactory.makeMovie())
        this.stubGetMovieGroups(Observable.just(movieGroups))
        this.stubGetWatchListMovies(Observable.just(movies))
        this.activity.launchActivity(null)

        onView(withContentDescription(R.string.abc_action_bar_up_description))
                .perform(click())

        onView(withText(R.string.watch_list))
                .perform(click())

        intended(hasComponent(WatchListFragment::class.java.name))
    }

    @Test
    fun settingsMenuOpensSettings() {
        val movieGroups = MoviesFactory.makeMovieGroups()
        this.stubGetMovieGroups(Observable.just(movieGroups))
        this.activity.launchActivity(null)

        onView(withContentDescription(R.string.abc_action_bar_up_description))
                .perform(click())

        onView(withText(R.string.settings))
                .perform(click())

        intended(hasComponent(SettingsActivity::class.java.name))
    }

    @Test
    fun viewMoreOpensMoreMovies() {
        val movieGroups = MoviesFactory.makeMovieGroups()
        this.stubGetMovieGroups(Observable.just(movieGroups))
        this.stubGetMovies(Observable.just(movieGroups[0].movies))

        this.activity.launchActivity(null)

        movieGroups.forEach {
            onView(allOf(withText(R.string.view_more), isCompletelyDisplayed()))
                    .perform(click())

            pressBack()

            onView(withId(R.id.viewPager))
                    .perform(swipeLeft())
        }

        intended(hasComponent(MoviesFragment::class.java.name), times(movieGroups.size))
    }

    private fun stubGetMovieGroups(observable: Observable<List<MovieGroup>>) {
        whenever(TestApplication.appComponent().moviesRepository().getMovieGroups())
                .thenReturn(observable)
    }

    private fun stubGetMovieDetails(observable: Observable<Movie>) {
        whenever(TestApplication.appComponent().moviesRepository().getMovieDetails(any()))
                .thenReturn(observable)
    }

    private fun stubGetWatchListMovies(observable: Observable<List<Movie>>) {
        whenever(TestApplication.appComponent().moviesRepository().getWatchListMovies())
                .thenReturn(observable)
    }

    private fun stubGetMovies(observable: Observable<List<Movie>>) {
        whenever(TestApplication.appComponent().moviesRepository().getMovies(any(), any()))
                .thenReturn(observable)
    }

}