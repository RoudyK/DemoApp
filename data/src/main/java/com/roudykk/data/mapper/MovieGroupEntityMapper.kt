package com.roudykk.data.mapper

import com.roudykk.data.model.MovieGroupEntity
import com.roudykk.domain.model.MovieGroup
import javax.inject.Inject

class MovieGroupEntityMapper @Inject constructor(
        private val movieEntityMapper: MovieEntityMapper
): Mapper<MovieGroupEntity, MovieGroup> {

    override fun mapFromEntity(entity: MovieGroupEntity): MovieGroup {
        return MovieGroup(title = entity.title,
                movies = entity.movies.map {
                    this.movieEntityMapper.mapFromEntity(it)
                })
    }

    override fun mapToEntity(domain: MovieGroup): MovieGroupEntity {
        return MovieGroupEntity(title = domain.title,
                movies = domain.movies.map {
                    this.movieEntityMapper.mapToEntity(it)
                })
    }

}