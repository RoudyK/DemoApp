package com.demo.roudykk.demoapp.injection.module

import com.nhaarman.mockitokotlin2.mock
import com.roudykk.data.repository.MoviesRemote
import com.roudykk.remote.ServicesFactory
import com.roudykk.remote.service.DiscoverApi
import com.roudykk.remote.service.MovieApi
import com.roudykk.remote.service.PersonApi
import com.roudykk.remote.service.SearchApi
import dagger.Module
import dagger.Provides

@Module
object TestRemoteModule {

    @Provides
    @JvmStatic
    fun provideDiscoverApi(): DiscoverApi {
        return ServicesFactory.discoverApi()
    }

    @Provides
    @JvmStatic
    fun provideMovieApi(): MovieApi {
        return ServicesFactory.movieApi()
    }

    @Provides
    @JvmStatic
    fun providePersonApi(): PersonApi {
        return ServicesFactory.personApi()
    }

    @Provides
    @JvmStatic
    fun provideSearchApi(): SearchApi {
        return ServicesFactory.searchApi()
    }

    @Provides
    @JvmStatic
    fun bindRemote(): MoviesRemote {
        return mock()
    }
}