package com.demo.roudykk.demoapp.injection.module

import com.demo.roudykk.demoapp.UiThread
import com.demo.roudykk.demoapp.ui.activity.*
import com.demo.roudykk.demoapp.ui.fragment.*
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
    abstract fun contributeMoviesActivity(): MoviesFragment

    @ContributesAndroidInjector
    abstract fun contributeMovieActivity(): MovieFragment

    @ContributesAndroidInjector
    abstract fun contributeWatchListActivity(): WatchListFragment

    @ContributesAndroidInjector
    abstract fun contributeSearchActivity(): SearchFragment

    @ContributesAndroidInjector
    abstract fun contributePersonDetailsFragment(): ProfileFragment

    @ContributesAndroidInjector
    abstract fun contributHomeFragment(): HomeFragment
}