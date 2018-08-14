package com.roudykk.cache.mapper

import com.roudykk.cache.model.CacheMovie
import com.roudykk.cache.test.MoviesCacheFactory
import com.roudykk.data.model.MovieEntity
import junit.framework.Assert.assertEquals
import org.junit.Test

class CacheMovieMapperTest {

    private val cacheMovieMapper = CacheMovieMapper()

    @Test
    fun mapFromCache() {
        val cacheMovie = MoviesCacheFactory.makeCacheMovie()
        val movieEntity = this.cacheMovieMapper.mapFromCache(cacheMovie)

        this.assertMovieEquals(movieEntity, cacheMovie)
    }

    @Test
    fun mapToCache() {
        val movieEntity = MoviesCacheFactory.makeMovieEntity()
        val cacheMovie  = this.cacheMovieMapper.mapToCache(movieEntity)

        this.assertMovieEquals(movieEntity, cacheMovie)
    }

    private fun assertMovieEquals(movieEntity: MovieEntity, movie: CacheMovie) {
        assertEquals(movieEntity.id, movie.id)
        assertEquals(movieEntity.title, movie.title)
        assertEquals(movieEntity.overview, movie.overview)
        assertEquals(movieEntity.video, movie.video)
        assertEquals(movieEntity.voteCount, movie.voteCount)
        assertEquals(movieEntity.voteAverage, movie.voteAverage)
        assertEquals(movieEntity.popularity, movie.popularity)
        assertEquals(movieEntity.posterPath, movie.posterPath)
        assertEquals(movieEntity.originalLanguage, movie.originalLanguage)
        assertEquals(movieEntity.originalTitle, movie.originalTitle)
        assertEquals(movieEntity.backdropPath, movie.backdropPath)
        assertEquals(movieEntity.posterPath, movie.posterPath)
        assertEquals(movieEntity.releaseDate, movie.releaseDate)
        assertEquals(movieEntity.revenue, movie.revenue)
        assertEquals(movieEntity.runtime, movie.runtime)
        assertEquals(movieEntity.budget, movie.budget)
        assertEquals(movieEntity.status, movie.status)
    }
}