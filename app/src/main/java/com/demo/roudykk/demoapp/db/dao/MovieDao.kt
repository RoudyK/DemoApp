package com.demo.roudykk.demoapp.db.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.demo.roudykk.demoapp.api.models.Movie

@Dao
interface MovieDao {

    @Query("select * from Movie")
    fun getAll(): LiveData<List<Movie>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg movies: Movie)

    @Query("delete from Movie where id == :id")
    fun delete(id: Int)

    @Query("delete from Movie")
    fun deleteAll()
}