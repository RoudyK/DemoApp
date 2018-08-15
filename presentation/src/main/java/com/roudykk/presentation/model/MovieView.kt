package com.roudykk.presentation.model

data class MovieView(
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
        var spokenLanguages: List<SpokenLanguageView>? = null,
        var status: String? = null,
        var tagLine: String? = null,
        var budget: Float,
        var genres: List<GenreView>? = null,
        var productionCompanies: List<ProductionCompanyView>? = null,
        var productionCountries: List<ProductionCountryView>? = null,
        var videos: List<VideoView>? = null,
        var reviews: List<ReviewView>? = null,
        var cast: List<PersonView>? = null,
        val crew: List<PersonView>? = null) {

    fun getImageUrl(): String {
        return "https://image.tmdb.org/t/p/w500$posterPath"
    }
}