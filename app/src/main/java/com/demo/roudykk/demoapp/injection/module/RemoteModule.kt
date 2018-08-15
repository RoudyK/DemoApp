package com.demo.roudykk.demoapp.injection.module

import com.demo.roudykk.demoapp.api.Api
import com.demo.roudykk.demoapp.api.endpoints.DiscoverApi
import com.demo.roudykk.demoapp.api.endpoints.MovieApi
import com.demo.roudykk.demoapp.api.endpoints.PersonApi
import com.roudykk.data.repository.MoviesRemote
import com.roudykk.remote.MoviesRemoteImpl
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
abstract class RemoteModule {

    @Binds
    abstract fun bindRemote(moviesRemoteImpl: MoviesRemoteImpl): MoviesRemote

    @Module
    companion object {

        @Provides
        @JvmStatic
        fun provideDiscoverApi(): DiscoverApi {
            return Api.discoverApi()
        }

        @Provides
        @JvmStatic
        fun provideMovieApi(): MovieApi {
            return Api.movieApi()
        }

        @Provides
        @JvmStatic
        fun providePersonApi(): PersonApi {
            return Api.personApi()
        }

    }
}