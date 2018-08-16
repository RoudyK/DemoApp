package com.demo.roudykk.demoapp.injection.module

import com.nhaarman.mockitokotlin2.mock
import com.roudykk.domain.repository.MoviesRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object TestDataModule {

    @Provides
    @JvmStatic
    @Singleton
    fun bindDataRepository(): MoviesRepository {
        return mock()
    }
}