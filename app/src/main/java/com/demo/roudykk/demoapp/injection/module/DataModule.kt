package com.demo.roudykk.demoapp.injection.module

import com.roudykk.data.MoviesDataRepository
import com.roudykk.domain.repository.MoviesRepository
import dagger.Binds
import dagger.Module

@Module
@Suppress("unused")
abstract class DataModule {

    @Binds
    abstract fun bindDataRepository(moviesDataRepository: MoviesDataRepository): MoviesRepository
}