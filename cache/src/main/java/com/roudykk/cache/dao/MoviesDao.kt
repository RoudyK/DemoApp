package com.roudykk.cache.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.roudykk.cache.db.MoviesConsts
import com.roudykk.cache.model.CacheMovie
import io.reactivex.Flowable

@Dao
interface MoviesDao {

    @Query(MoviesConsts.QUERY_GET_MOVIES)
    fun getMovies(): Flowable<List<CacheMovie>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg movies: CacheMovie)

    @Query(MoviesConsts.QUERY_DELETE_MOVIE)
    fun delete(id: Int)

    @Query(MoviesConsts.QUERY_CLEAR_MOVIES)
    fun clearMovies()
}