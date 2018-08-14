package com.roudykk.cache.db

object MoviesConsts {

    const val TABLE_NAME = "movies"

    const val QUERY_GET_MOVIES = "select * from $TABLE_NAME"

    const val COLUMN_MOVIE_ID = "movie_id"

    const val QUERY_DELETE_MOVIE = "delete from $TABLE_NAME where $COLUMN_MOVIE_ID == :id"

    const val QUERY_CLEAR_MOVIES = "delete from $TABLE_NAME"
}