package com.roudykk.remote.mapper

import com.roudykk.data.model.MovieEntity
import com.roudykk.remote.model.CreditsModel
import com.roudykk.remote.model.MovieModel
import com.roudykk.remote.model.ReviewResultModel
import com.roudykk.remote.model.VideoResultModel
import javax.inject.Inject

open class MovieModelMapper @Inject constructor(
        private val spokenLanguageMapper: SpokenLanguageModelMapper,
        private val productionCompanyMapper: ProductionCompanyModelMapper,
        private val productionCountryMapper: ProductionCountryModelMapper,
        private val genreMapper: GenreModelMapper,
        private val videoMapper: VideoModelMapper,
        private val reviewMapper: ReviewModelMapper,
        private val personMapper: PersonModelMapper
) : ModelMapper<MovieModel, MovieEntity> {

    override fun mapToModel(entity: MovieEntity): MovieModel {
        return MovieModel(id = entity.id,
                title = entity.title,
                overview = entity.overview,
                video = entity.video,
                vote_average = entity.voteAverage,
                vote_count = entity.voteCount,
                popularity = entity.popularity,
                poster_path = entity.posterPath,
                original_language = entity.originalLanguage,
                original_title = entity.originalTitle,
                genre_ids = entity.genreIds,
                backdrop_path = entity.backdropPath,
                release_date = entity.releaseDate,
                revenue = entity.revenue,
                runtime = entity.runtime,
                status = entity.status,
                tagline = entity.tagLine,
                budget = entity.budget,
                genres = entity.genres?.map {
                    this.genreMapper.mapToModel(it)
                },
                spoken_languages = entity.spokenLanguages?.map {
                    this.spokenLanguageMapper.mapToModel(it)
                },
                production_companies = entity.productionCompanies?.map {
                    this.productionCompanyMapper.mapToModel(it)
                },
                production_countries = entity.productionCountries?.map {
                    this.productionCountryMapper.mapToModel(it)
                },
                videos = VideoResultModel(
                        results = entity.videos?.map {
                            this.videoMapper.mapToModel(it)
                        }
                ),
                reviews = ReviewResultModel(
                        results = entity.reviews?.map {
                            this.reviewMapper.mapToModel(it)
                        }
                ),
                credits = CreditsModel(
                        cast = entity.cast?.map {
                            this.personMapper.mapToModel(it)
                        },
                        crew = entity.crew?.map {
                            this.personMapper.mapToModel(it)
                        }
                ))
    }

    override fun mapFromModel(model: MovieModel): MovieEntity {
        return MovieEntity(id = model.id,
                title = model.title,
                overview = model.overview,
                video = model.video,
                voteAverage = model.vote_average,
                voteCount = model.vote_count,
                popularity = model.popularity,
                posterPath = model.poster_path,
                originalLanguage = model.original_language,
                originalTitle = model.original_title,
                genreIds = model.genre_ids,
                backdropPath = model.backdrop_path,
                releaseDate = model.release_date,
                revenue = model.revenue,
                runtime = model.runtime,
                status = model.status,
                tagLine = model.tagline,
                budget = model.budget,
                genres = model.genres?.map {
                    this.genreMapper.mapFromModel(it)
                },
                spokenLanguages = model.spoken_languages?.map {
                    this.spokenLanguageMapper.mapFromModel(it)
                },
                productionCompanies = model.production_companies?.map {
                    this.productionCompanyMapper.mapFromModel(it)
                },
                productionCountries = model.production_countries?.map {
                    this.productionCountryMapper.mapFromModel(it)
                },
                videos = model.videos?.results?.map {
                    this.videoMapper.mapFromModel(it)
                },
                reviews = model.reviews?.results?.map {
                    this.reviewMapper.mapFromModel(it)
                },
                cast = model.credits?.cast?.map {
                    this.personMapper.mapFromModel(it)
                },
                crew = model.credits?.crew?.map {
                    this.personMapper.mapFromModel(it)
                })
    }

}