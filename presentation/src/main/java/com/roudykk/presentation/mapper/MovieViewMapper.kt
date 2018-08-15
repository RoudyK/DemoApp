package com.roudykk.presentation.mapper

import com.roudykk.domain.model.Movie
import com.roudykk.presentation.model.MovieView
import javax.inject.Inject

open class MovieViewMapper @Inject constructor(
        private val spokenLanguageViewMapper: SpokenLanguageViewMapper,
        private val productionCompanyEntityMapper: ProductionCompanyEntityMapper,
        private val productionCountryViewMapper: ProductionCountryViewMapper,
        private val genreEntityMapper: GenreViewMapper,
        private val videoViewMapper: VideoViewMapper,
        private val reviewViewMapper: ReviewViewMapper,
        private val personViewMapper: PersonViewMapper
) : ViewMapper<MovieView, Movie> {

    override fun mapFromView(view: MovieView): Movie {
        return Movie(id = view.id,
                title = view.title,
                overview = view.overview,
                video = view.video,
                voteAverage = view.voteAverage,
                voteCount = view.voteCount,
                popularity = view.popularity,
                posterPath = view.posterPath,
                originalLanguage = view.originalLanguage,
                originalTitle = view.originalTitle,
                genreIds = view.genreIds,
                backdropPath = view.backdropPath,
                releaseDate = view.releaseDate,
                revenue = view.revenue,
                runtime = view.runtime,
                status = view.status,
                tagLine = view.tagLine,
                budget = view.budget,
                genres = view.genres?.map {
                    this.genreEntityMapper.mapFromView(it)
                },
                spokenLanguages = view.spokenLanguages?.map {
                    this.spokenLanguageViewMapper.mapFromView(it)
                },
                productionCompanies = view.productionCompanies?.map {
                    this.productionCompanyEntityMapper.mapFromView(it)
                },
                productionCountries = view.productionCountries?.map {
                    this.productionCountryViewMapper.mapFromView(it)
                },
                videos = view.videos?.map {
                    this.videoViewMapper.mapFromView(it)
                },
                reviews = view.reviews?.map {
                    this.reviewViewMapper.mapFromView(it)
                },
                cast = view.cast?.map {
                    this.personViewMapper.mapFromView(it)
                },
                crew = view.crew?.map {
                    this.personViewMapper.mapFromView(it)
                })
    }

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