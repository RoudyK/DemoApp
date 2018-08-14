package com.roudykk.cache.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import com.roudykk.cache.dao.MoviesDao
import com.roudykk.cache.model.CacheMovie

@Database(
        entities = [CacheMovie::class],
        version = 1)
abstract class MoviesDatabase : RoomDatabase() {

    abstract fun moviesDao(): MoviesDao

    companion object {
        private var INSTANCE: MoviesDatabase? = null
        private val lock = Any()

        fun getInstance(context: Context): MoviesDatabase {
            if (INSTANCE == null) {
                synchronized(lock) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                            MoviesDatabase::class.java, "movies.db").build()
                }
            }
            return INSTANCE as MoviesDatabase
        }
    }
}