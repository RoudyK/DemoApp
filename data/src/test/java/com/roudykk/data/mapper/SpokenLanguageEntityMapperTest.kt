package com.roudykk.data.mapper

import com.roudykk.data.test.MoviesAssertionUtil
import com.roudykk.data.test.MoviesEntityFactory
import org.junit.Test

class SpokenLanguageEntityMapperTest : EntityMapperTest {

    private val spokenLanguageEntityMapper = SpokenLanguageEntityMapper()

     @Test
    override fun mapFromEntity() {
        val spokenLanguageEntity = MoviesEntityFactory.makeSpokenLanguageEntity()
        val spokenLanguage = this.spokenLanguageEntityMapper.mapFromEntity(spokenLanguageEntity)

        MoviesAssertionUtil.assertSpokenLanguageEquals(spokenLanguageEntity, spokenLanguage)
    }

    @Test
    override fun mapToEntity() {
        val spokenLanguage = MoviesEntityFactory.makeSpokenLanguage()
        val spokenLanguageEntity = this.spokenLanguageEntityMapper.mapToEntity(spokenLanguage)

        MoviesAssertionUtil.assertSpokenLanguageEquals(spokenLanguageEntity, spokenLanguage)
    }

}