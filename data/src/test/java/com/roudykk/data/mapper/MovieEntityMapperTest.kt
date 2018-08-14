package com.roudykk.data.mapper

import com.roudykk.data.test.MoviesAssertionUtil
import com.roudykk.data.test.MoviesEntityFactory
import org.junit.Test

class MovieEntityMapperTest: EntityMapperTest{
    private val movieEntityMapper = MovieEntityMapper(
            SpokenLanguageEntityMapper(),
            ProductionCompanyEntityMapper(),
            ProductionCountryEntityMapper(),
            GenreEntityMapper(),
            VideoEntityMapper(),
            ReviewEntityMapper(),
            PersonEntityMapper())

    @Test
    override fun mapFromEntity() {
        val movieEntity = MoviesEntityFactory.makeMovieEntity()
        val movie = this.movieEntityMapper.mapFromEntity(movieEntity)

        MoviesAssertionUtil.assertMovieEquals(movieEntity, movie)
    }

    @Test
    override fun mapToEntity() {
        val movie = MoviesEntityFactory.makeMovie()
        val movieEntity = this.movieEntityMapper.mapToEntity(movie)

        MoviesAssertionUtil.assertMovieEquals(movieEntity, movie)
    }

}