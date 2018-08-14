package com.roudykk.remote.mapper

import com.roudykk.data.model.ReviewEntity
import com.roudykk.domain.model.Review
import com.roudykk.remote.model.ReviewModel
import javax.inject.Inject

class ReviewModelMapper @Inject constructor(): ModelMapper<ReviewModel, ReviewEntity>{

    override fun mapToModel(entity: ReviewEntity): ReviewModel {
        return ReviewModel(id = entity.id,
                author = entity.author,
                content = entity.content,
                url = entity.url)
    }

    override fun mapFromModel(model: ReviewModel): ReviewEntity {
        return ReviewEntity(id = model.id,
                author = model.author,
                content = model.content,
                url = model.url)
    }

}