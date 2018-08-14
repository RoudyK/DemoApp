package com.roudykk.data.mapper

import com.roudykk.data.test.MoviesAssertionUtil
import com.roudykk.data.test.MoviesEntityFactory
import org.junit.Test

class ReviewEntityMapperTest : EntityMapperTest {
    private val reviewEntityMapper = ReviewEntityMapper()

    @Test
    override fun mapFromEntity() {
        val reviewEntity = MoviesEntityFactory.makeReviewEntity()
        val review = this.reviewEntityMapper.mapFromEntity(reviewEntity)

        MoviesAssertionUtil.assertReviewEquals(reviewEntity, review)
    }

    @Test
    override fun mapToEntity() {
        val review = MoviesEntityFactory.makeReview()
        val reviewEntity = this.reviewEntityMapper.mapToEntity(review)

        MoviesAssertionUtil.assertReviewEquals(reviewEntity, review)
    }

}