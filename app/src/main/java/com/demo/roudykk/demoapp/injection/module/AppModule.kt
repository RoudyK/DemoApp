package com.demo.roudykk.demoapp.injection.module

import com.demo.roudykk.demoapp.MainActivity
import com.demo.roudykk.demoapp.features.home.HomeFragment
import com.demo.roudykk.demoapp.features.movie.MovieFragment
import com.demo.roudykk.demoapp.features.movies.MoviesFragment
import com.demo.roudykk.demoapp.features.profile.ProfileFragment
import com.demo.roudykk.demoapp.features.search.SearchFragment
import com.demo.roudykk.demoapp.features.watchlist.WatchListFragment
import com.demo.roudykk.demoapp.injection.UiThread
import com.roudykk.domain.executor.PostExecutionThread
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
@Suppress("unused")
abstract class AppModule {

    @Binds
    abstract fun bindExecutionThread(uiThread: UiThread): PostExecutionThread

    @ContributesAndroidInjector
    abstract fun contributeMainActivity(): MainActivity

    @ContributesAndroidInjector
    abstract fun contributeMoviesFragment(): MoviesFragment

    @ContributesAndroidInjector
    abstract fun contributeMovieFragment(): MovieFragment

    @ContributesAndroidInjector
    abstract fun contributeWatchListFragment(): WatchListFragment

    @ContributesAndroidInjector
    abstract fun contributeSearchFragment(): SearchFragment

    @ContributesAndroidInjector
    abstract fun contributePersonDetailsFragment(): ProfileFragment

    @ContributesAndroidInjector
    abstract fun contributeHomeFragment(): HomeFragment
}