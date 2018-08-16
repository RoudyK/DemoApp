package com.demo.roudykk.demoapp.test

import com.roudykk.domain.model.*
import java.util.*
import java.util.concurrent.ThreadLocalRandom

object MoviesFactory {

    fun randomString(): String = UUID.randomUUID().toString()

    fun randomInt(): Int = ThreadLocalRandom.current().nextInt(0, 1000 + 1)

    fun randomLong(): Long = this.randomInt().toLong()

    fun randomFloat(): Float = this.randomInt().toFloat()

    fun randomBoolean(): Boolean = Math.random() < 0.5

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
                listOf(this.makeMovie(), this.makeMovie()),
                this.randomInt(), this.randomInt(), this.randomString())
    }

    fun makeMovieGroups(): List<MovieGroup> {
        return listOf(this.makeMovieGroup(), this.makeMovieGroup())
    }
}