package com.roudykk.cache.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.roudykk.cache.db.MoviesConsts

@Entity(tableName = MoviesConsts.TABLE_NAME)
data class CacheMovie(
        @PrimaryKey
        @ColumnInfo(name = MoviesConsts.COLUMN_MOVIE_ID)
        var id: Int,
        var title: String? = null,
        var overview: String? = null,
        var video: Boolean? = null,
        var voteCount: Int? = null,
        var voteAverage: Float,
        var popularity: Float? = null,
        var posterPath: String? = null,
        var originalLanguage: String? = null,
        var originalTitle: String? = null,
        var backdropPath: String? = null,
        var releaseDate: String? = null,
        var revenue: Float? = null,
        var runtime: Int? = null,
        var status: String? = null,
        var tagLine: String? = null,
        var budget: Float)