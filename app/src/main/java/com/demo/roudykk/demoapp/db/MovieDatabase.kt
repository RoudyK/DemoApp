package com.demo.roudykk.demoapp.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import com.demo.roudykk.demoapp.api.models.Movie
import com.demo.roudykk.demoapp.db.dao.MovieDao

@Database(entities = [Movie::class], version = 4)
abstract class MovieDatabase : RoomDatabase() {

    abstract fun movieDao(): MovieDao

    companion object {
        private var INSTANCE: MovieDatabase? = null
        private const val DATABASE_NAME = "movie.db"

        fun getInstance(context: Context): MovieDatabase? {
            if (INSTANCE == null) {
                synchronized(MovieDatabase::class) {
                    INSTANCE = Room.databaseBuilder(
                            context.applicationContext,
                            MovieDatabase::class.java,
                            DATABASE_NAME)
                            .fallbackToDestructiveMigration()
                            .allowMainThreadQueries()
                            .build()
                }
            }
            return INSTANCE
        }

    }
}