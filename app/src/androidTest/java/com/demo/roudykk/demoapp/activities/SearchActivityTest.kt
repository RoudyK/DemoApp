package com.demo.roudykk.demoapp.activities

import android.app.Activity
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.ActivityResultMatchers.hasResultCode
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.matcher.RootMatchers.withDecorView
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.rule.ActivityTestRule
import com.demo.roudykk.demoapp.R
import com.demo.roudykk.demoapp.TestApplication
import com.demo.roudykk.demoapp.test.MoviesFactory
import com.demo.roudykk.demoapp.ui.activity.MovieActivity
import com.demo.roudykk.demoapp.ui.activity.SearchActivity
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.whenever
import com.roudykk.domain.model.Movie
import io.reactivex.Observable
import org.hamcrest.Matchers.*
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class SearchActivityTest {

    @Rule
    @JvmField
    var activity = ActivityTestRule<SearchActivity>(SearchActivity::class.java, false, false)

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
        this.stubSearchMovies(Observable.just(listOf()))
        this.activity.launchActivity(null)
    }

    @Test
    fun activityDisplaysResults() {
        val movies = listOf(MoviesFactory.makeMovie(), MoviesFactory.makeMovie())
        this.stubSearchMovies(Observable.just(movies))
        this.activity.launchActivity(null)

        onView(withId(R.id.searchEt))
                .perform(click())
                .perform(typeText("a"))
                .perform(typeText("b"))

        movies.forEach { movie ->
            onView(withId(R.id.moviesRv))
                    .check(matches(hasDescendant(withText(movie.title))))
        }

    }

    @Test
    fun activityDisplaysError() {
        this.stubSearchMovies(Observable.error(RuntimeException()))
        this.activity.launchActivity(null)

        onView(withId(R.id.searchEt))
                .perform(click())
                .perform(typeText("a"))
                .perform(typeText("b"))

        onView(withText(R.string.failed_load_movie))
                .inRoot(withDecorView(not(`is`(this.activity.activity.window.decorView))))
                .check(matches(isDisplayed()))
    }

    @Test
    fun activityRecyclerItemsOpenMovie() {
        val movies = listOf(MoviesFactory.makeMovie(), MoviesFactory.makeMovie())
        this.stubSearchMovies(Observable.just(movies))
        val movie = MoviesFactory.makeMovie()
        this.stubGetMovieDetails(Observable.just(movie))

        this.activity.launchActivity(null)

        onView(withId(R.id.searchEt))
                .perform(click())
                .perform(typeText("a"))
                .perform(typeText("b"))

        onView(allOf(hasDescendant(withText(movies[0].title)),
                withContentDescription(R.string.movie_container)))
                .perform(click())

        intended(hasComponent(MovieActivity::class.java.name))


    }

    @Test
    fun activityMenuClosesScreen() {
        this.stubSearchMovies(Observable.just(listOf()))
        this.activity.launchActivity(null)

        onView(withContentDescription(R.string.abc_action_bar_up_description))
                .perform(click())

        assertThat(activity.activityResult, hasResultCode(Activity.RESULT_CANCELED))

    }


    private fun stubSearchMovies(observable: Observable<List<Movie>>) {
        whenever(TestApplication.appComponent().moviesRepository().searchMovies(any()))
                .thenReturn(observable)
    }

    private fun stubGetMovieDetails(observable: Observable<Movie>) {
        whenever(TestApplication.appComponent().moviesRepository().getMovieDetails(any()))
                .thenReturn(observable)
    }
}