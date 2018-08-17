package com.demo.roudykk.demoapp.analytics

import android.content.Context
import com.demo.roudykk.demoapp.analytics.base.BaseAnalytics
import com.demo.roudykk.demoapp.analytics.base.EventBundle
import com.demo.roudykk.demoapp.analytics.base.Tracker
import com.demo.roudykk.demoapp.analytics.consts.Event
import com.demo.roudykk.demoapp.analytics.consts.Param
import com.demo.roudykk.demoapp.analytics.consts.Source
import com.demo.roudykk.demoapp.analytics.mixpanel.MixpanelTracker

class Analytics() : BaseAnalytics, Tracker {
    private val trackersPool: MutableList<Tracker> = mutableListOf()

    constructor(context: Context) : this() {
        trackersPool.add(MixpanelTracker(context))
    }

    override fun track(event: Event, bundle: EventBundle) {
        trackersPool.forEach { tracker ->
            tracker.track(event, bundle)
        }
    }

    override fun userOpenedHome() {
        this.track(Event.EVENT_USER_OPENED_HOME, EventBundle())
    }

    override fun userOpenedMovie(movieId: Int, source: Source) {
        val bundle = EventBundle()
        bundle.put(Param.PARAM_MOVIE_ID, movieId)
        bundle.put(Param.PARAM_SOURCE, source)
        this.track(Event.EVENT_USER_OPENED_MOVIE, bundle)
    }

    override fun userOpenedWatchList() {
        this.track(Event.EVENT_USER_OPENED_WATCH_LIST, EventBundle())
    }

    override fun userOpenedMoreMovies(category: String) {
        val bundle = EventBundle()
        bundle.put(Param.PARAM_CATEGORY, category)
        this.track(Event.EVENT_USER_OPENED_MORE_MOVIES, bundle)
    }

    override fun userOpenedSearch() {
        this.track(Event.EVENT_USER_OPENED_SEARCH, EventBundle())
    }

    override fun userSearched(searchQuery: String) {
        val bundle = EventBundle()
        bundle.put(Param.PARAM_SEARCH_QUERY, searchQuery)
        this.track(Event.EVENT_USER_SEARCHED, bundle)
    }

    override fun userAddedMovieWatchList(movieId: Int) {
        val bundle = EventBundle()
        bundle.put(Param.PARAM_MOVIE_ID, movieId)
        this.track(Event.EVENT_USER_ADDED_MOVIE_WATCH_LIST, bundle)
    }

    override fun userDeletedMovieWatchList(movieId: Int) {
        val bundle = EventBundle()
        bundle.put(Param.PARAM_MOVIE_ID, movieId)
        this.track(Event.EVENT_USER_DELETED_MOVIE_WATCH_LIST, bundle)
    }

    override fun userDeletedAllMoviesWatchList(moviesCount: Int) {
        val bundle = EventBundle()
        bundle.put(Param.PARAM_MOVIES_COUNT, moviesCount)
        this.track(Event.EVENT_USER_DELETED_ALL_MOVIES_WATCH_LIST, bundle)
    }

    override fun userClickedMovieReadMore() {
        this.track(Event.EVENT_USER_CLICKED_MOVIE_READ_MORE, EventBundle())
    }

    companion object {
        private var INSTANCE: Analytics? = null

        fun getInstance(context: Context): Analytics {
            if (INSTANCE == null) {
                INSTANCE = Analytics(context)
            }
            return INSTANCE as Analytics
        }
    }

}