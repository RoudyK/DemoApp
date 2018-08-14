package com.roudykk.data.mapper

import com.roudykk.data.test.MoviesAssertionUtil
import com.roudykk.data.test.MoviesEntityFactory
import org.junit.Test

class VideoEntityMapperTest : EntityMapperTest {

    private val videoEntityMapper = VideoEntityMapper()

    @Test
    override fun mapFromEntity() {
        val videoEntity = MoviesEntityFactory.makeVideoEntity()
        val video = this.videoEntityMapper.mapFromEntity(videoEntity)

        MoviesAssertionUtil.assertVideoEquals(videoEntity, video)
    }

    @Test
    override fun mapToEntity() {
        val video = MoviesEntityFactory.makeVideo()
        val videoEntity = this.videoEntityMapper.mapToEntity(video)

        MoviesAssertionUtil.assertVideoEquals(videoEntity, video)
    }

}