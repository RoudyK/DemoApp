package com.roudykk.remote.test

import com.roudykk.data.model.*
import com.roudykk.remote.model.*
import java.util.*
import java.util.concurrent.ThreadLocalRandom

object MoviesRemoteFactory {
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
                listOf(this.makeMovieEntity(), this.makeMovieEntity()), this.randomString())
    }

    fun makeMovieGroupsEntity(): List<MovieGroupEntity> {
        return listOf(this.makeMovieGroupEntity(), this.makeMovieGroupEntity())
    }

    fun makeSpokenLanguageMode(): SpokenLanguageModel{
        return SpokenLanguageModel(
                this.randomString(),
                this.randomString())
    }

    fun makeGenreModel(): GenreModel {
        return GenreModel(
                this.randomInt(),
                this.randomString())
    }

    fun makeProductionCompanyModel(): ProductionCompanyModel {
        return ProductionCompanyModel(
                this.randomInt(),
                this.randomString(),
                this.randomString(),
                this.randomString())
    }

    fun makeProductionCountryModel(): ProductionCountryModel {
        return ProductionCountryModel(
                this.randomString(),
                this.randomString())
    }

    fun makeVideoModel(): VideoModel {
        return VideoModel(
                this.randomString(),
                this.randomString(),
                this.randomString(),
                this.randomString(),
                this.randomString(),
                this.randomString(),
                this.randomString())
    }

    fun makeReviewModel(): ReviewModel {
        return ReviewModel(
                this.randomString(),
                this.randomString(),
                this.randomString(),
                this.randomString())
    }

    fun makePersonModel(): PersonModel {
        return PersonModel(
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

    fun makeMovieModel(): MovieModel {
        return MovieModel(
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
                listOf(this.makeSpokenLanguageMode(), this.makeSpokenLanguageMode()),
                this.randomString(),
                this.randomString(),
                this.randomFloat(),
                listOf(this.makeGenreModel(), this.makeGenreModel()),
                listOf(this.makeProductionCompanyModel(), this.makeProductionCompanyModel()),
                listOf(this.makeProductionCountryModel(), this.makeProductionCountryModel()),
                VideoResultModel(listOf(this.makeVideoModel(), this.makeVideoModel())),
                ReviewResultModel(
                        this.randomInt(),
                        listOf(this.makeReviewModel(), this.makeReviewModel()),
                        this.randomInt(),
                        this.randomInt()),
                CreditsModel(
                        cast = listOf(this.makePersonModel(), this.makePersonModel()),
                        crew = listOf(this.makePersonModel(), this.makePersonModel())
                ))
    }

    fun makeMoviesResultModel(): MoviesResultModel {
        return MoviesResultModel(
                this.randomString(),
                this.randomString(),
                this.randomInt(),
                this.randomInt(),
                this.randomInt(),
                listOf(this.makeMovieModel(), this.makeMovieModel())
        )
    }
}