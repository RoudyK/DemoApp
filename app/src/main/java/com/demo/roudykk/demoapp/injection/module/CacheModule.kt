package com.demo.roudykk.demoapp.injection.module

import android.app.Application
import com.roudykk.cache.MoviesCacheImpl
import com.roudykk.cache.db.MoviesDatabase
import com.roudykk.data.repository.MoviesCache
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
@Suppress("unused")
abstract class CacheModule {

    @Module
    companion object {

        @Provides
        @JvmStatic
        fun providesDatabase(application: Application): MoviesDatabase {
            return MoviesDatabase.getInstance(application.applicationContext)
        }
    }

    @Binds
    abstract fun bindCache(moviesCacheImpl: MoviesCacheImpl): MoviesCache
}