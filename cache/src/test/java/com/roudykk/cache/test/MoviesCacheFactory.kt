package com.roudykk.cache.test

import com.roudykk.cache.model.CacheMovie
import com.roudykk.data.model.*
import java.util.*
import java.util.concurrent.ThreadLocalRandom

object MoviesCacheFactory {

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

    fun makeMovieEntityStripped(): MovieEntity {
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
                null,
                this.randomString(),
                this.randomString(),
                this.randomFloat(),
                this.randomInt(),
                null,
                this.randomString(),
                this.randomString(),
                this.randomFloat(),
                null,
                null,
                null,
                null,
                null,
                null,
                null)
    }

    fun makeMovieGroupEntity(): MovieGroupEntity {
        return MovieGroupEntity(
                this.randomString(),
                listOf(this.makeMovieEntity(), this.makeMovieEntity()),
                this.randomInt(), this.randomInt(), this.randomString())
    }

    fun makeCacheMovie(): CacheMovie {
        return CacheMovie(
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
                this.randomString(),
                this.randomString(),
                this.randomFloat(),
                this.randomInt(),
                this.randomString(),
                this.randomString(),
                this.randomFloat())
    }
}