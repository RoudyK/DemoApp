package com.demo.roudykk.demoapp.injection.module

import com.demo.roudykk.demoapp.UiThread
import com.demo.roudykk.demoapp.ui.activity.MainActivity
import com.demo.roudykk.demoapp.ui.activity.MovieActivity
import com.demo.roudykk.demoapp.ui.activity.MoviesActivity
import com.demo.roudykk.demoapp.ui.activity.WatchListActivity
import com.demo.roudykk.demoapp.ui.fragment.PersonDetailsFragment
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
    abstract fun contributePersonDetailsFragment(): PersonDetailsFragment
}