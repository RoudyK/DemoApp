package com.roudykk.remote.mapper

import com.roudykk.data.model.MovieGroupEntity
import com.roudykk.remote.model.MoviesResultModel
import javax.inject.Inject

open class MovieGroupModelMapper @Inject constructor(
        private val movieMapper: MovieModelMapper
) : ModelMapper<MoviesResultModel, MovieGroupEntity> {

    override fun mapToModel(entity: MovieGroupEntity): MoviesResultModel {
        return MoviesResultModel(title = entity.title,
                results = entity.movies.map {
                    this.movieMapper.mapToModel(it)
                },
                index = entity.index)
    }

    override fun mapFromModel(model: MoviesResultModel): MovieGroupEntity {
        return MovieGroupEntity(title = model.title,
                movies = model.results.map {
                    this.movieMapper.mapFromModel(it)
                }, index = model.index,
                page = model.page,
                totalPages = model.total_pages)
    }

}