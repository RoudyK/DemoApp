package com.roudykk.presentation.mapper

import com.roudykk.domain.model.Genre
import com.roudykk.presentation.model.GenreView
import javax.inject.Inject

class GenreViewMapper @Inject constructor() : ViewMapper<GenreView, Genre> {

    override fun mapFromView(view: GenreView): Genre {
        return Genre(id = view.id,
                name = view.name)
    }

    override fun mapToView(domain: Genre): GenreView {
        return GenreView(id = domain.id,
                name = domain.name)
    }

}