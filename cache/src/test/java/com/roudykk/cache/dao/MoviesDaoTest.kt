package com.roudykk.cache.dao

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.persistence.room.Room
import com.roudykk.cache.db.MoviesDatabase
import com.roudykk.cache.test.MoviesCacheFactory
import org.junit.After
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment

@RunWith(RobolectricTestRunner::class)
class MoviesDaoTest {
    @Rule
    @JvmField
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private val database = Room.inMemoryDatabaseBuilder(
            RuntimeEnvironment.application.applicationContext,
            MoviesDatabase::class.java)
            .allowMainThreadQueries()
            .build()

    @After
    fun closeDb() {
        database.close()
    }

    @Test
    fun getMoviesReturnsData() {
        val cacheMovie = MoviesCacheFactory.makeCacheMovie()

        this.database.moviesDao().insert(cacheMovie)
        val observer = this.database.moviesDao().getMovies().test()

        observer.assertValue(listOf(cacheMovie))
    }

    @Test
    fun deleteMovieDeletesData() {
        val cacheMovie = MoviesCacheFactory.makeCacheMovie()

        this.database.moviesDao().insert(cacheMovie)
        this.database.moviesDao().delete(cacheMovie.id)

        val observer = this.database.moviesDao().getMovies().test()
        observer.assertValue(listOf())
    }

    @Test
    fun clearMoviesDeletesData() {
        val cacheMovie = MoviesCacheFactory.makeCacheMovie()
        val cacheMovie2 = MoviesCacheFactory.makeCacheMovie()

        this.database.moviesDao().insert(cacheMovie, cacheMovie2)
        this.database.moviesDao().clearMovies()

        val observer = this.database.moviesDao().getMovies().test()
        observer.assertValue(listOf())
    }
}