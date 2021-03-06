package com.roudykk.presentation.mapper

import com.roudykk.domain.model.Review
import com.roudykk.presentation.model.ReviewView
import javax.inject.Inject

class ReviewViewMapper @Inject constructor() : ViewMapper<ReviewView, Review> {

    override fun mapFromView(view: ReviewView): Review {
        return Review(id = view.id,
                author = view.author,
                content = view.content,
                url = view.url)
    }

    override fun mapToView(domain: Review): ReviewView {
        return ReviewView(id = domain.id,
                author = domain.author,
                content = domain.content,
                url = domain.url)
    }

}