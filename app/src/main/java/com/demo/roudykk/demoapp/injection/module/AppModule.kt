package com.demo.roudykk.demoapp.injection.module

import com.demo.roudykk.demoapp.UiThread
import com.demo.roudykk.demoapp.ui.activity.*
import com.demo.roudykk.demoapp.ui.fragment.HomeFragment
import com.demo.roudykk.demoapp.ui.fragment.MovieGroupFragment
import com.demo.roudykk.demoapp.ui.fragment.PersonDetailsFragment
import com.demo.roudykk.demoapp.ui.fragment.SearchFragment
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
    abstract fun contributeMoviesActivity(): MoviesActivity

    @ContributesAndroidInjector
    abstract fun contributeMovieActivity(): MovieActivity

    @ContributesAndroidInjector
    abstract fun contributeWatchListActivity(): WatchListActivity

    @ContributesAndroidInjector
    abstract fun contributeSearchActivity(): SearchFragment

    @ContributesAndroidInjector
    abstract fun contributePersonDetailsFragment(): PersonDetailsFragment

    @ContributesAndroidInjector
    abstract fun contributeMovieGroupFragment(): MovieGroupFragment

    @ContributesAndroidInjector
    abstract fun contributHomeFragment(): HomeFragment
}