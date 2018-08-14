package com.roudykk.data.mapper

import com.roudykk.data.model.MovieEntity
import com.roudykk.domain.model.Movie
import javax.inject.Inject

open class MovieEntityMapper @Inject constructor(
        private val spokenLanguageEntityMapper: SpokenLanguageEntityMapper,
        private val productionCompanyEntityMapper: ProductionCompanyEntityMapper,
        private val productionCountryEntityMapper: ProductionCountryEntityMapper,
        private val genreEntityMapper: GenreEntityMapper,
        private val videoEntityMapper: VideoEntityMapper,
        private val reviewEntityMapper: ReviewEntityMapper,
        private val personEntityMapper: PersonEntityMapper
) : Mapper<MovieEntity, Movie> {

    override fun mapFromEntity(entity: MovieEntity): Movie {
        return Movie(id = entity.id,
                title = entity.title,
                overview = entity.overview,
                video = entity.video,
                voteAverage = entity.voteAverage,
                voteCount = entity.voteCount,
                popularity = entity.popularity,
                posterPath = entity.posterPath,
                originalLanguage = entity.originalLanguage,
                originalTitle = entity.originalTitle,
                genreIds = entity.genreIds,
                backdropPath = entity.backdropPath,
                releaseDate = entity.releaseDate,
                revenue = entity.revenue,
                runtime = entity.runtime,
                status = entity.status,
                tagLine = entity.tagLine,
                budget = entity.budget,
                genres = entity.genres?.map {
                    this.genreEntityMapper.mapFromEntity(it)
                },
                spokenLanguages = entity.spokenLanguages?.map {
                    this.spokenLanguageEntityMapper.mapFromEntity(it)
                },
                productionCompanies = entity.productionCompanies?.map {
                    this.productionCompanyEntityMapper.mapFromEntity(it)
                },
                productionCountries = entity.productionCountries?.map {
                    this.productionCountryEntityMapper.mapFromEntity(it)
                },
                videos = entity.videos?.map {
                    this.videoEntityMapper.mapFromEntity(it)
                },
                reviews = entity.reviews?.map {
                    this.reviewEntityMapper.mapFromEntity(it)
                },
                cast = entity.cast?.map {
                    this.personEntityMapper.mapFromEntity(it)
                },
                crew = entity.crew?.map {
                    this.personEntityMapper.mapFromEntity(it)
                })
    }

    override fun mapToEntity(domain: Movie): MovieEntity {
        return MovieEntity(id = domain.id,
                title = domain.title,
                overview = domain.overview,
                video = domain.video,
                voteAverage = domain.voteAverage,
                voteCount = domain.voteCount,
                popularity = domain.popularity,
                posterPath = domain.posterPath,
                originalLanguage = domain.originalLanguage,
                originalTitle = domain.originalTitle,
                genreIds = domain.genreIds,
                backdropPath = domain.backdropPath,
                releaseDate = domain.releaseDate,
                revenue = domain.revenue,
                runtime = domain.runtime,
                status = domain.status,
                tagLine = domain.tagLine,
                budget = domain.budget,
                genres = domain.genres?.map {
                    this.genreEntityMapper.mapToEntity(it)
                },
                spokenLanguages = domain.spokenLanguages?.map {
                    this.spokenLanguageEntityMapper.mapToEntity(it)
                },
                productionCompanies = domain.productionCompanies?.map {
                    this.productionCompanyEntityMapper.mapToEntity(it)
                },
                productionCountries = domain.productionCountries?.map {
                    this.productionCountryEntityMapper.mapToEntity(it)
                },
                videos = domain.videos?.map {
                    this.videoEntityMapper.mapToEntity(it)
                },
                reviews = domain.reviews?.map {
                    this.reviewEntityMapper.mapToEntity(it)
                },
                cast = domain.cast?.map {
                    this.personEntityMapper.mapToEntity(it)
                },
                crew = domain.crew?.map {
                    this.personEntityMapper.mapToEntity(it)
                })
    }

}