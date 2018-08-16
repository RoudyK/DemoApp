package com.demo.roudykk.demoapp.activities

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.intent.Intents
import android.support.test.espresso.intent.Intents.intended
import android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.rule.ActivityTestRule
import com.demo.roudykk.demoapp.R
import com.demo.roudykk.demoapp.TestApplication
import com.demo.roudykk.demoapp.test.MoviesFactory
import com.demo.roudykk.demoapp.ui.activity.*
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
            movieGroup.movies.forEach { movie ->
                onView(withId(R.id.homeRv))
                        .check(matches(hasDescendant(withText(movie.title))))
            }
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

        intended(hasComponent(MovieActivity::class.java.name))
    }

    @Test
    fun activitySearchMenuOpensSearch() {
        val movieGroups = MoviesFactory.makeMovieGroups()
        this.stubGetMovieGroups(Observable.just(movieGroups))
        this.activity.launchActivity(null)

        onView(withId(R.id.nav_search))
                .perform(click())

        intended(hasComponent(SearchActivity::class.java.name))
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

        intended(hasComponent(WatchListActivity::class.java.name))
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
}