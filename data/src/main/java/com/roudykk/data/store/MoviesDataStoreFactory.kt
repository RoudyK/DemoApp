package com.roudykk.data.store

import com.roudykk.data.repository.MoviesDataStore
import javax.inject.Inject

open class MoviesDataStoreFactory @Inject constructor(
        private val moviesCacheDataStore: MoviesCacheDataStore,
        private val moviesRemoteDataStore: MoviesRemoteDataStore) {

    open fun getCacheDataStore(): MoviesDataStore = this.moviesCacheDataStore

    open fun getRemoteDataStore(): MoviesDataStore = this.moviesRemoteDataStore

}