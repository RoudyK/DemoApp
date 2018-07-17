package com.demo.roudykk.demoapp.analytics.consts

enum class Event(val value: String) {
    EVENT_USER_OPENED_HOME("User Opened Home"),
    EVENT_USER_OPENED_MOVIE("User Opened Movie"),
    EVENT_USER_OPENED_WATCH_LIST("User Opened Watch List"),
    EVENT_USER_OPENED_MORE_MOVIES("User Opened More Movies"),
    EVENT_USER_SEARCHED("User Searched"),
    EVENT_USER_OPENED_SEARCH("User Opened Search"),
    EVENT_USER_ADDED_MOVIE_WATCH_LIST("User Added Movie Watch List"),
    EVENT_USER_DELETED_MOVIE_WATCH_LIST("User Deleted Movie Watch List"),
    EVENT_USER_DELETED_ALL_MOVIES_WATCH_LIST("User Deleted All Movies Watch List"),
    EVENT_USER_CLICKED_MOVIE_READ_MORE("User Clicked Movie Read More")
}