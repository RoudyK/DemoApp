package com.demo.roudykk.demoapp.injection.module

import android.app.Application
import com.nhaarman.mockitokotlin2.mock
import com.roudykk.cache.db.MoviesDatabase
import com.roudykk.data.repository.MoviesCache
import dagger.Module
import dagger.Provides

@Module
object TestCacheModule {

    @Provides
    @JvmStatic
    fun providesDatabase(application: Application): MoviesDatabase {
        return MoviesDatabase.getInstance(application.applicationContext)
    }

    @Provides
    @JvmStatic
    fun bindCache(): MoviesCache {
        return mock()
    }
}