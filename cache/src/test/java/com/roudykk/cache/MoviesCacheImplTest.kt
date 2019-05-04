package com.roudykk.cache

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import com.roudykk.cache.db.MoviesDatabase
import com.roudykk.cache.mapper.CacheMovieMapper
import com.roudykk.cache.test.MoviesCacheFactory
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment

@RunWith(RobolectricTestRunner::class)
class MoviesCacheImplTest {

    @Rule
    @JvmField
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private val database = Room.inMemoryDatabaseBuilder(
            RuntimeEnvironment.application.applicationContext,
            MoviesDatabase::class.java)
            .allowMainThreadQueries()
            .build()

    private val mapper = CacheMovieMapper()
    private val cache = MoviesCacheImpl(this.database, this.mapper)

    @Test
    fun addMovieWatchListCompletes() {
        val movie = MoviesCacheFactory.makeMovieEntity()
        this.cache.addMovieWatchList(movie).test().assertComplete()
    }

    @Test
    fun removeMovieWatchListCompletes() {
        val movie = MoviesCacheFactory.makeMovieEntityStripped()
        val movie2 = MoviesCacheFactory.makeMovieEntityStripped()

        this.cache.addMovieWatchList(movie).test()
        this.cache.addMovieWatchList(movie2).test()
        this.cache.removeMovieWatchlist(movie.id).test()

        val observer = this.cache.getWatchListMovies().test()

        observer.assertValue(listOf(movie2))
    }

    @Test
    fun getMoviesReturnsData() {
        val movie = MoviesCacheFactory.makeMovieEntityStripped()

        this.cache.addMovieWatchList(movie).test()

        val observer = this.cache.getWatchListMovies().test()
        observer.assertValue(listOf(movie))
    }

    @Test
    fun clearMoviesClearsData() {
        val movie = MoviesCacheFactory.makeMovieEntityStripped()
        val movie2 = MoviesCacheFactory.makeMovieEntityStripped()

        this.cache.addMovieWatchList(movie).test()
        this.cache.addMovieWatchList(movie2).test()
        this.cache.clearWatchListMovies().test()

        val observer = this.cache.getWatchListMovies().test()
        observer.assertValue(listOf())
    }
}