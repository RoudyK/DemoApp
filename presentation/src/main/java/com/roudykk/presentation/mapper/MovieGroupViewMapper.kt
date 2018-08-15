package com.roudykk.presentation.mapper

import com.roudykk.domain.model.MovieGroup
import com.roudykk.presentation.model.MovieGroupView
import javax.inject.Inject

open class MovieGroupViewMapper @Inject constructor(
        private val movieViewMapper: MovieViewMapper
) : ViewMapper<MovieGroupView, MovieGroup> {

    override fun mapFromView(view: MovieGroupView): MovieGroup {
        return MovieGroup(title = view.title,
                movies = view.movies.map {
                    this.movieViewMapper.mapFromView(it)
                }, index = view.index)
    }

    override fun mapToView(domain: MovieGroup): MovieGroupView {
        return MovieGroupView(title = domain.title,
                movies = domain.movies.map {
                    this.movieViewMapper.mapToView(it)
                }, index = domain.index)
    }

}