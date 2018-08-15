package com.roudykk.presentation.mapper

import com.roudykk.domain.model.MovieGroup
import com.roudykk.presentation.model.MovieGroupView
import javax.inject.Inject

open class MovieGroupViewMapper @Inject constructor(
        private val movieEntityMapper: MovieEntityMapper
) : ViewMapper<MovieGroupView, MovieGroup> {

    override fun mapToView(domain: MovieGroup): MovieGroupView {
        return MovieGroupView(title = domain.title,
                movies = domain.movies.map {
                    this.movieEntityMapper.mapToView(it)
                }, index = domain.index)
    }

}