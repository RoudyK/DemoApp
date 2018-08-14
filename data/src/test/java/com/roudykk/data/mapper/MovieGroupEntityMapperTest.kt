package com.roudykk.data.mapper

import com.roudykk.data.test.MoviesAssertionUtil
import com.roudykk.data.test.MoviesEntityFactory
import org.junit.Test

class MovieGroupEntityMapperTest : EntityMapperTest {
    private val movieGroupEntityMapper = MovieGroupEntityMapper(
            MovieEntityMapper(SpokenLanguageEntityMapper(),
                    ProductionCompanyEntityMapper(),
                    ProductionCountryEntityMapper(),
                    GenreEntityMapper(),
                    VideoEntityMapper(),
                    ReviewEntityMapper(),
                    PersonEntityMapper()))

    @Test
    override fun mapFromEntity() {
        val movieGroupEntity = MoviesEntityFactory.makeMovieGroupEntity()
        val movieGroup = this.movieGroupEntityMapper.mapFromEntity(movieGroupEntity)

        MoviesAssertionUtil.assertMovieGroupEquals(movieGroupEntity, movieGroup)
    }

    @Test
    override fun mapToEntity() {
        val movieGroup = MoviesEntityFactory.makeMovieGroup()
        val movieGroupEntity = this.movieGroupEntityMapper.mapToEntity(movieGroup)

        MoviesAssertionUtil.assertMovieGroupEquals(movieGroupEntity, movieGroup)
    }

}