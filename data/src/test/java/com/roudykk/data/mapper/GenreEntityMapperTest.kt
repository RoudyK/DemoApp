package com.roudykk.data.mapper

import com.roudykk.data.test.MoviesAssertionUtil
import com.roudykk.data.test.MoviesEntityFactory
import org.junit.Test

class GenreEntityMapperTest: EntityMapperTest {

    private val genreEntityMapper = GenreEntityMapper()

    @Test
    override fun mapFromEntity() {
        val genreEntity = MoviesEntityFactory.makeGenreEntity()
        val genre = this.genreEntityMapper.mapFromEntity(genreEntity)

        MoviesAssertionUtil.assertGenresEquals(genreEntity, genre)
    }

    @Test
    override fun mapToEntity() {
        val genre = MoviesEntityFactory.makeGenre()
        val genreEntity = this.genreEntityMapper.mapToEntity(genre)

        MoviesAssertionUtil.assertGenresEquals(genreEntity, genre)
    }
}