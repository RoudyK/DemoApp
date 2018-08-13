package com.roudykk.data.mapper

import com.roudykk.data.model.ReviewEntity
import com.roudykk.domain.model.Review
import javax.inject.Inject

class ReviewEntityMapper @Inject constructor(): Mapper<ReviewEntity, Review>{

    override fun mapFromEntity(entity: ReviewEntity): Review {
        return Review(id = entity.id,
                author = entity.author,
                content = entity.content,
                url = entity.url)
    }

    override fun mapToEntity(domain: Review): ReviewEntity {
        return ReviewEntity(id = domain.id,
                author = domain.author,
                content = domain.content,
                url = domain.url)
    }

}