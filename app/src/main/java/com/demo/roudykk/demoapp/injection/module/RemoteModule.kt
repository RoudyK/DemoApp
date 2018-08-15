package com.demo.roudykk.demoapp.injection.module

import com.roudykk.data.repository.MoviesRemote
import com.roudykk.remote.MoviesRemoteImpl
import com.roudykk.remote.ServicesFactory
import com.roudykk.remote.service.DiscoverApi
import com.roudykk.remote.service.MovieApi
import com.roudykk.remote.service.PersonApi
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
@Suppress("unused")
abstract class RemoteModule {

    @Binds
    abstract fun bindRemote(moviesRemoteImpl: MoviesRemoteImpl): MoviesRemote

    @Module
    companion object {

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

    }
}