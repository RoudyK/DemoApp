package com.roudykk.data.store

import com.roudykk.data.repository.MoviesDataStore
import javax.inject.Inject

class MoviesDataStoreFactory @Inject constructor(
        private val moviesCacheDataStore: MoviesCacheDataStore,
        private val moviesRemoteDataStore: MoviesRemoteDataStore) {

    fun getCacheDataStore(): MoviesDataStore = this.moviesCacheDataStore

    fun getRemoteDataStore(): MoviesDataStore = this.moviesRemoteDataStore

}