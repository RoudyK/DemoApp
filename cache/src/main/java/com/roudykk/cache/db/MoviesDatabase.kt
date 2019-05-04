package com.roudykk.cache.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.roudykk.cache.dao.MoviesDao
import com.roudykk.cache.model.CacheMovie

@Database(entities = [CacheMovie::class], version = 1)
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