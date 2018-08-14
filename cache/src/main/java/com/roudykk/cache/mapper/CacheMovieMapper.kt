package com.roudykk.cache.mapper

import com.roudykk.cache.model.CacheMovie
import com.roudykk.data.model.MovieEntity

class CacheMovieMapper : CacheMapper<CacheMovie, MovieEntity> {

    override fun mapFromCache(cache: CacheMovie): MovieEntity {
        return MovieEntity(id = cache.id,
                title = cache.title,
                overview = cache.overview,
                video = cache.video,
                voteAverage = cache.voteAverage,
                voteCount = cache.voteCount,
                popularity = cache.popularity,
                posterPath = cache.posterPath,
                originalLanguage = cache.originalLanguage,
                originalTitle = cache.originalTitle,
                backdropPath = cache.backdropPath,
                releaseDate = cache.releaseDate,
                revenue = cache.revenue,
                runtime = cache.runtime,
                status = cache.status,
                tagLine = cache.tagLine,
                budget = cache.budget)
    }

    override fun mapToCache(entity: MovieEntity): CacheMovie {
        return CacheMovie(id = entity.id,
                title = entity.title,
                overview = entity.overview,
                video = entity.video,
                voteAverage = entity.voteAverage,
                voteCount = entity.voteCount,
                popularity = entity.popularity,
                posterPath = entity.posterPath,
                originalLanguage = entity.originalLanguage,
                originalTitle = entity.originalTitle,
                backdropPath = entity.backdropPath,
                releaseDate = entity.releaseDate,
                revenue = entity.revenue,
                runtime = entity.runtime,
                status = entity.status,
                tagLine = entity.tagLine,
                budget = entity.budget)
    }

}