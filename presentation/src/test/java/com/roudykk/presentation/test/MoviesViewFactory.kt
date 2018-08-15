package com.roudykk.presentation.test

import com.roudykk.domain.model.*
import com.roudykk.presentation.model.*
import java.util.*
import java.util.concurrent.ThreadLocalRandom

object MoviesViewFactory {

    fun randomString(): String = UUID.randomUUID().toString()

    fun randomInt(): Int = ThreadLocalRandom.current().nextInt(0, 1000 + 1)

    fun randomLong(): Long = randomInt().toLong()

    fun randomFloat(): Float = randomInt().toFloat()

    fun randomBoolean(): Boolean = Math.random() < 0.5

    fun makeSpokenLanguage(): SpokenLanguage {
        return SpokenLanguage(
                randomString(),
                randomString())
    }

    fun makeGenre(): Genre {
        return Genre(
                randomInt(),
                randomString())
    }

    fun makeProductionCompany(): ProductionCompany {
        return ProductionCompany(
                randomInt(),
                randomString(),
                randomString(),
                randomString())
    }

    fun makeProductionCountry(): ProductionCountry {
        return ProductionCountry(
                randomString(),
                randomString())
    }

    fun makeVideo(): Video {
        return Video(
                randomString(),
                randomString(),
                randomString(),
                randomString(),
                randomString(),
                randomString(),
                randomString())
    }

    fun makeReview(): Review {
        return Review(
                randomString(),
                randomString(),
                randomString(),
                randomString())
    }

    fun makePerson(): Person {
        return Person(
                randomInt(),
                randomString(),
                randomString(),
                randomString(),
                randomString(),
                randomInt(),
                randomInt(),
                randomString(),
                randomString(),
                randomString(),
                randomString(),
                randomString(),
                randomFloat(),
                randomString(),
                randomBoolean(),
                arrayListOf(randomString(), randomString()))
    }

    fun makeMovie(): Movie {
        return Movie(
                randomInt(),
                randomString(),
                randomString(),
                randomBoolean(),
                randomInt(),
                randomFloat(),
                randomFloat(),
                randomString(),
                randomString(),
                randomString(),
                arrayListOf(randomInt(), randomInt()),
                randomString(),
                randomString(),
                randomFloat(),
                randomInt(),
                arrayListOf(makeSpokenLanguage(), makeSpokenLanguage()),
                randomString(),
                randomString(),
                randomFloat(),
                arrayListOf(makeGenre(), makeGenre()),
                arrayListOf(makeProductionCompany(), makeProductionCompany()),
                arrayListOf(makeProductionCountry(), makeProductionCountry()),
                arrayListOf(makeVideo(), makeVideo()),
                arrayListOf(makeReview(), makeReview()),
                arrayListOf(makePerson(), makePerson()),
                arrayListOf(makePerson(), makePerson()))
    }

    fun makeMovieGroup(): MovieGroup {
        return MovieGroup(
                randomString(),
                listOf(makeMovie(), makeMovie()),
                this.randomInt(), this.randomInt(), randomString())
    }

    fun makeMovieGroups(): List<MovieGroup> {
        return listOf(makeMovieGroup(), makeMovieGroup())
    }


    fun makeSpokenLanguageView(): SpokenLanguageView {
        return SpokenLanguageView(
                randomString(),
                randomString())
    }

    fun makeGenreView(): GenreView {
        return GenreView(
                randomInt(),
                randomString())
    }

    fun makeProductionCompanyView(): ProductionCompanyView {
        return ProductionCompanyView(
                randomInt(),
                randomString(),
                randomString(),
                randomString())
    }

    fun makeProductionCountryView(): ProductionCountryView {
        return ProductionCountryView(
                randomString(),
                randomString())
    }

    fun makeVideoView(): VideoView {
        return VideoView(
                randomString(),
                randomString(),
                randomString(),
                randomString(),
                randomString(),
                randomString(),
                randomString())
    }

    fun makeReviewView(): ReviewView {
        return ReviewView(
                randomString(),
                randomString(),
                randomString(),
                randomString())
    }

    fun makePersonView(): PersonView {
        return PersonView(
                randomInt(),
                randomString(),
                randomString(),
                randomString(),
                randomString(),
                randomInt(),
                randomInt(),
                randomString(),
                randomString(),
                randomString(),
                randomString(),
                randomString(),
                randomFloat(),
                randomString(),
                randomBoolean(),
                arrayListOf(randomString(), randomString()))
    }

    fun makeMovieView(): MovieView {
        return MovieView(
                randomInt(),
                randomString(),
                randomString(),
                randomBoolean(),
                randomInt(),
                randomFloat(),
                randomFloat(),
                randomString(),
                randomString(),
                randomString(),
                arrayListOf(randomInt(), randomInt()),
                randomString(),
                randomString(),
                randomFloat(),
                randomInt(),
                arrayListOf(makeSpokenLanguageView(), makeSpokenLanguageView()),
                randomString(),
                randomString(),
                randomFloat(),
                arrayListOf(makeGenreView(), makeGenreView()),
                arrayListOf(makeProductionCompanyView(), makeProductionCompanyView()),
                arrayListOf(makeProductionCountryView(), makeProductionCountryView()),
                arrayListOf(makeVideoView(), makeVideoView()),
                arrayListOf(makeReviewView(), makeReviewView()),
                arrayListOf(makePersonView(), makePersonView()),
                arrayListOf(makePersonView(), makePersonView()))
    }

    fun makeMovieGroupView(): MovieGroupView {
        return MovieGroupView(
                randomString(),
                mutableListOf(makeMovieView(), makeMovieView()),
                this.randomInt(), this.randomInt(), randomString())
    }

    fun makeMovieGroupsView(): List<MovieGroupView> {
        return listOf(makeMovieGroupView(), makeMovieGroupView())
    }
}