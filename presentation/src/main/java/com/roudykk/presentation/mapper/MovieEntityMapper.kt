package com.roudykk.presentation.mapper

import com.roudykk.domain.model.Movie
import com.roudykk.presentation.model.MovieView
import javax.inject.Inject

open class MovieEntityMapper @Inject constructor(
        private val spokenLanguageViewMapper: SpokenLanguageViewMapper,
        private val productionCompanyEntityMapper: ProductionCompanyEntityMapper,
        private val productionCountryViewMapper: ProductionCountryViewMapper,
        private val genreEntityMapper: GenreViewMapper,
        private val videoViewMapper: VideoViewMapper,
        private val reviewViewMapper: ReviewViewMapper,
        private val personViewMapper: PersonViewMapper
) : ViewMapper<MovieView, Movie> {

    override fun mapToView(domain: Movie): MovieView {
        return MovieView(id = domain.id,
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
                    this.genreEntityMapper.mapToView(it)
                },
                spokenLanguages = domain.spokenLanguages?.map {
                    this.spokenLanguageViewMapper.mapToView(it)
                },
                productionCompanies = domain.productionCompanies?.map {
                    this.productionCompanyEntityMapper.mapToView(it)
                },
                productionCountries = domain.productionCountries?.map {
                    this.productionCountryViewMapper.mapToView(it)
                },
                videos = domain.videos?.map {
                    this.videoViewMapper.mapToView(it)
                },
                reviews = domain.reviews?.map {
                    this.reviewViewMapper.mapToView(it)
                },
                cast = domain.cast?.map {
                    this.personViewMapper.mapToView(it)
                },
                crew = domain.crew?.map {
                    this.personViewMapper.mapToView(it)
                })
    }

}