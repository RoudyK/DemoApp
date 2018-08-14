package com.roudykk.data.test

import com.roudykk.data.model.*
import com.roudykk.domain.model.*
import java.util.*
import java.util.concurrent.ThreadLocalRandom

object MoviesEntityFactory {

    fun randomString(): String = UUID.randomUUID().toString()

    fun randomInt(): Int = ThreadLocalRandom.current().nextInt(0, 1000 + 1)

    fun randomLong(): Long = this.randomInt().toLong()

    fun randomFloat(): Float = this.randomInt().toFloat()

    fun randomBoolean(): Boolean = Math.random() < 0.5

    fun makeSpokenLanguageEntity(): SpokenLanguageEntity {
        return SpokenLanguageEntity(
                this.randomString(),
                this.randomString())
    }

    fun makeGenreEntity(): GenreEntity {
        return GenreEntity(
                this.randomInt(),
                this.randomString())
    }

    fun makeProductionCompanyEntity(): ProductionCompanyEntity {
        return ProductionCompanyEntity(
                this.randomInt(),
                this.randomString(),
                this.randomString(),
                this.randomString())
    }

    fun makeProductionCountryEntity(): ProductionCountryEntity {
        return ProductionCountryEntity(
                this.randomString(),
                this.randomString())
    }

    fun makeVideoEntity(): VideoEntity {
        return VideoEntity(
                this.randomString(),
                this.randomString(),
                this.randomString(),
                this.randomString(),
                this.randomString(),
                this.randomString(),
                this.randomString())
    }

    fun makeReviewEntity(): ReviewEntity {
        return ReviewEntity(
                this.randomString(),
                this.randomString(),
                this.randomString(),
                this.randomString())
    }

    fun makePersonEntity(): PersonEntity {
        return PersonEntity(
                this.randomInt(),
                this.randomString(),
                this.randomString(),
                this.randomString(),
                this.randomString(),
                this.randomInt(),
                this.randomInt(),
                this.randomString(),
                this.randomString(),
                this.randomString(),
                this.randomString(),
                this.randomString(),
                this.randomFloat(),
                this.randomString(),
                this.randomBoolean(),
                arrayListOf(this.randomString(), this.randomString()))
    }

    fun makeMovieEntity(): MovieEntity {
        return MovieEntity(
                this.randomInt(),
                this.randomString(),
                this.randomString(),
                this.randomBoolean(),
                this.randomInt(),
                this.randomFloat(),
                this.randomFloat(),
                this.randomString(),
                this.randomString(),
                this.randomString(),
                arrayListOf(this.randomInt(), this.randomInt()),
                this.randomString(),
                this.randomString(),
                this.randomFloat(),
                this.randomInt(),
                arrayListOf(this.makeSpokenLanguageEntity(), this.makeSpokenLanguageEntity()),
                this.randomString(),
                this.randomString(),
                this.randomFloat(),
                arrayListOf(this.makeGenreEntity(), this.makeGenreEntity()),
                arrayListOf(this.makeProductionCompanyEntity(), this.makeProductionCompanyEntity()),
                arrayListOf(this.makeProductionCountryEntity(), this.makeProductionCountryEntity()),
                arrayListOf(this.makeVideoEntity(), this.makeVideoEntity()),
                arrayListOf(this.makeReviewEntity(), this.makeReviewEntity()),
                arrayListOf(this.makePersonEntity(), this.makePersonEntity()),
                arrayListOf(this.makePersonEntity(), this.makePersonEntity()))
    }

    fun makeMovieGroupEntity(): MovieGroupEntity {
        return MovieGroupEntity(
                this.randomString(),
                listOf(this.makeMovieEntity(), this.makeMovieEntity()))
    }

    fun makeMovieGroupsEntity(): List<MovieGroupEntity> {
        return listOf(this.makeMovieGroupEntity(), this.makeMovieGroupEntity())
    }

    fun makeSpokenLanguage(): SpokenLanguage {
        return SpokenLanguage(
                this.randomString(),
                this.randomString())
    }

    fun makeGenre(): Genre {
        return Genre(
                this.randomInt(),
                this.randomString())
    }

    fun makeProductionCompany(): ProductionCompany {
        return ProductionCompany(
                this.randomInt(),
                this.randomString(),
                this.randomString(),
                this.randomString())
    }

    fun makeProductionCountry(): ProductionCountry {
        return ProductionCountry(
                this.randomString(),
                this.randomString())
    }

    fun makeVideo(): Video {
        return Video(
                this.randomString(),
                this.randomString(),
                this.randomString(),
                this.randomString(),
                this.randomString(),
                this.randomString(),
                this.randomString())
    }

    fun makeReview(): Review {
        return Review(
                this.randomString(),
                this.randomString(),
                this.randomString(),
                this.randomString())
    }

    fun makePerson(): Person {
        return Person(
                this.randomInt(),
                this.randomString(),
                this.randomString(),
                this.randomString(),
                this.randomString(),
                this.randomInt(),
                this.randomInt(),
                this.randomString(),
                this.randomString(),
                this.randomString(),
                this.randomString(),
                this.randomString(),
                this.randomFloat(),
                this.randomString(),
                this.randomBoolean(),
                arrayListOf(this.randomString(), this.randomString()))
    }

    fun makeMovie(): Movie {
        return Movie(
                this.randomInt(),
                this.randomString(),
                this.randomString(),
                this.randomBoolean(),
                this.randomInt(),
                this.randomFloat(),
                this.randomFloat(),
                this.randomString(),
                this.randomString(),
                this.randomString(),
                arrayListOf(this.randomInt(), this.randomInt()),
                this.randomString(),
                this.randomString(),
                this.randomFloat(),
                this.randomInt(),
                arrayListOf(this.makeSpokenLanguage(), this.makeSpokenLanguage()),
                this.randomString(),
                this.randomString(),
                this.randomFloat(),
                arrayListOf(this.makeGenre(), this.makeGenre()),
                arrayListOf(this.makeProductionCompany(), this.makeProductionCompany()),
                arrayListOf(this.makeProductionCountry(), this.makeProductionCountry()),
                arrayListOf(this.makeVideo(), this.makeVideo()),
                arrayListOf(this.makeReview(), this.makeReview()),
                arrayListOf(this.makePerson(), this.makePerson()),
                arrayListOf(this.makePerson(), this.makePerson()))
    }

    fun makeMovieGroup(): MovieGroup {
        return MovieGroup(
                this.randomString(),
                listOf(this.makeMovie(), this.makeMovie()))
    }

    fun makeMovieGroups(): List<MovieGroup> {
        return listOf(this.makeMovieGroup(), this.makeMovieGroup())
    }
}