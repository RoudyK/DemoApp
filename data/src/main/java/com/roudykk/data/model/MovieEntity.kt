package com.roudykk.data.model

import java.util.*

data class MovieEntity(
        var id: Int,
        var title: String? = null,
        var overview: String? = null,
        var video: Boolean? = null,
        var voteCount: Int? = null,
        var voteAverage: Float,
        var popularity: Float? = null,
        var posterPath: String? = null,
        var originalLanguage: String? = null,
        var originalTitle: String? = null,
        var genreIds: List<Int>? = null,
        var backdropPath: String? = null,
        var releaseDate: String? = null,
        var revenue: Float? = null,
        var runtime: Int? = null,
        var spokenLanguages: List<SpokenLanguageEntity>? = null,
        var status: String? = null,
        var tagLine: String? = null,
        var budget: Float,
        var genres: List<GenreEntity>? = null,
        var productionCompanies: List<ProductionCompanyEntity>? = null,
        var productionCountries: List<ProductionCountryEntity>? = null,
        var videos: List<VideoEntity>? = null,
        var reviews: List<ReviewEntity>? = null,
        var cast: List<PersonEntity>? = null,
        val crew: List<PersonEntity>? = null)