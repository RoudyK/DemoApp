package com.roudykk.data.test

import com.roudykk.data.model.*
import com.roudykk.domain.model.*
import junit.framework.Assert.assertEquals

object MoviesAssertionUtil {

    fun assertGenresEquals(genreEntity: GenreEntity, genre: Genre) {
        assertEquals(genreEntity.id, genre.id)
        assertEquals(genreEntity.name, genre.name)
    }

    fun assertPersonEquals(personEntity: PersonEntity, person: Person) {
        assertEquals(personEntity.id, person.id)
        assertEquals(personEntity.name, person.name)
        assertEquals(personEntity.adult, person.adult)
        assertEquals(personEntity.knownFor, person.knownFor)
        assertEquals(personEntity.knownAs, person.knownAs)
        assertEquals(personEntity.biography, person.biography)
        assertEquals(personEntity.birthday, person.birthday)
        assertEquals(personEntity.castId, person.castId)
        assertEquals(personEntity.creditId, person.creditId)
        assertEquals(personEntity.character, person.character)
        assertEquals(personEntity.birthday, person.birthday)
        assertEquals(personEntity.deathday, person.deathday)
        assertEquals(personEntity.placeOfBirth, person.placeOfBirth)
        assertEquals(personEntity.profilePath, person.profilePath)
        assertEquals(personEntity.gender, person.gender)
        assertEquals(personEntity.popularity, person.popularity)
    }

    fun assertProductionCompanyEquals(productionCompanyEntity: ProductionCompanyEntity,
                                      productionCompany: ProductionCompany) {
        assertEquals(productionCompanyEntity.id, productionCompany.id)
        assertEquals(productionCompanyEntity.name, productionCompany.name)
        assertEquals(productionCompanyEntity.logoPath, productionCompany.logoPath)
        assertEquals(productionCompanyEntity.originCountry, productionCompany.originCountry)
    }

    fun assertProductionCountryEquals(productionCountryEntity: ProductionCountryEntity,
                                      productionCountry: ProductionCountry) {
        assertEquals(productionCountryEntity.isoName, productionCountry.isoName)
        assertEquals(productionCountryEntity.name, productionCountry.name)
    }

    fun assertReviewEquals(reviewEntity: ReviewEntity, review: Review) {
        assertEquals(reviewEntity.id, review.id)
        assertEquals(reviewEntity.author, review.author)
        assertEquals(reviewEntity.content, review.content)
        assertEquals(reviewEntity.url, review.url)
    }

    fun assertSpokenLanguageEquals(spokenLanguageEntity: SpokenLanguageEntity,
                                   spokenLanguage: SpokenLanguage) {
        assertEquals(spokenLanguageEntity.isoName, spokenLanguage.isoName)
        assertEquals(spokenLanguageEntity.name, spokenLanguage.name)
    }

    fun assertVideoEquals(videoEntity: VideoEntity, video: Video) {
        assertEquals(videoEntity.id, video.id)
        assertEquals(videoEntity.name, video.name)
        assertEquals(videoEntity.isoName, video.isoName)
        assertEquals(videoEntity.key, video.key)
        assertEquals(videoEntity.site, video.site)
        assertEquals(videoEntity.size, video.size)
        assertEquals(videoEntity.type, video.type)
    }

    fun assertMovieEquals(movieEntity: MovieEntity, movie: Movie) {
        assertEquals(movieEntity.id, movie.id)
        assertEquals(movieEntity.title, movie.title)
        assertEquals(movieEntity.overview, movie.overview)
        assertEquals(movieEntity.video, movie.video)
        assertEquals(movieEntity.voteCount, movie.voteCount)
        assertEquals(movieEntity.voteAverage, movie.voteAverage)
        assertEquals(movieEntity.popularity, movie.popularity)
        assertEquals(movieEntity.posterPath, movie.posterPath)
        assertEquals(movieEntity.originalLanguage, movie.originalLanguage)
        assertEquals(movieEntity.originalTitle, movie.originalTitle)
        assertEquals(movieEntity.genreIds, movie.genreIds)
        assertEquals(movieEntity.backdropPath, movie.backdropPath)
        assertEquals(movieEntity.posterPath, movie.posterPath)
        assertEquals(movieEntity.releaseDate, movie.releaseDate)
        assertEquals(movieEntity.revenue, movie.revenue)
        assertEquals(movieEntity.runtime, movie.runtime)
        assertEquals(movieEntity.budget, movie.budget)
        assertEquals(movieEntity.status, movie.status)
        movieEntity.genres?.forEachIndexed { index, _ ->
            this.assertGenresEquals(movieEntity.genres!![index],
                    movie.genres!![index])
        }
        movieEntity.spokenLanguages?.forEachIndexed { index, _ ->
            this.assertSpokenLanguageEquals(movieEntity.spokenLanguages!![index],
                    movie.spokenLanguages!![index])
        }
        movieEntity.productionCompanies?.forEachIndexed { index, _ ->
            this.assertProductionCompanyEquals(movieEntity.productionCompanies!![index],
                    movie.productionCompanies!![index])
        }
        movieEntity.productionCountries?.forEachIndexed { index, _ ->
            this.assertProductionCountryEquals(movieEntity.productionCountries!![index],
                    movie.productionCountries!![index])
        }
        movieEntity.videos?.forEachIndexed { index, _ ->
            this.assertVideoEquals(movieEntity.videos!![index],
                    movie.videos!![index])
        }
        movieEntity.reviews?.forEachIndexed { index, _ ->
            this.assertReviewEquals(movieEntity.reviews!![index],
                    movie.reviews!![index])
        }
        movieEntity.cast?.forEachIndexed { index, _ ->
            this.assertPersonEquals(movieEntity.cast!![index],
                    movie.cast!![index])
        }
        movieEntity.crew?.forEachIndexed { index, _ ->
            this.assertPersonEquals(movieEntity.crew!![index],
                    movie.crew!![index])
        }
    }

    fun assertMovieGroupEquals(movieGroupEntity: MovieGroupEntity, movieGroup: MovieGroup) {
        assertEquals(movieGroupEntity.title, movieGroup.title)
        movieGroupEntity.movies.forEachIndexed { index, _ ->
            this.assertMovieEquals(movieGroupEntity.movies[index],
                    movieGroup.movies[index])
        }
    }
}