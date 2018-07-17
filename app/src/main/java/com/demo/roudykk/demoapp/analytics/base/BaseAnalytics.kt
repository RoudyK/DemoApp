package com.demo.roudykk.demoapp.analytics.base

import com.demo.roudykk.demoapp.analytics.consts.Source

interface BaseAnalytics {

    fun userOpenedHome()

    fun userOpenedMovie(movieId: Int, source: Source)

    fun userOpenedWatchList()

    fun userOpenedMoreMovies(category: String)

    fun userOpenedSearch()

    fun userSearched(searchQuery: String)

    fun userAddedMovieWatchList(movieId: Int)

    fun userDeletedMovieWatchList(movieId: Int)

    fun userDeletedAllMoviesWatchList(moviesCount: Int)

    fun userClickedMovieReadMore()
}