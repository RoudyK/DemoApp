package com.demo.roudykk.demoapp.injection.module

import androidx.lifecycle.ViewModel
import com.demo.roudykk.demoapp.injection.ViewModelKey
import com.roudykk.presentation.viewmodel.*
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import javax.inject.Singleton

@Module
@Suppress("unused")
abstract class PresentationModule {

    @Binds
    @IntoMap
    @Singleton
    @ViewModelKey(HomeViewModel::class)
    abstract fun bindHomeViewModel(homeViewModel: HomeViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MoviesViewModel::class)
    abstract fun bindMoviesViewModel(moviesViewModel: MoviesViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MovieViewModel::class)
    abstract fun bindMovieViewModel(movieViewModel: MovieViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(PersonViewModel::class)
    abstract fun bindPersonViewModel(personViewModel: PersonViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(WatchListViewModel::class)
    abstract fun bindWatchListViewModel(watchListViewModel: WatchListViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SearchViewModel::class)
    abstract fun bindSearchViewModel(searchViewModel: SearchViewModel): ViewModel
}