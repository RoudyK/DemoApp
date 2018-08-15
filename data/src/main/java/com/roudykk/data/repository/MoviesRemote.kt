package com.roudykk.data.repository

import com.roudykk.data.model.MovieEntity
import com.roudykk.data.model.MovieGroupEntity
import com.roudykk.data.model.PersonEntity
import io.reactivex.Observable

interface MoviesRemote {

    fun getMovieGroups(): Observable<List<MovieGroupEntity>>

    fun getMovieDetails(movieId: Int): Observable<MovieEntity>

    fun getMovies(index: String, page: Int): Observable<List<MovieEntity>>

    fun getPersonDetails(personId: Int): Observable<PersonEntity>

    fun searchMovies(searchQuery: String): Observable<List<MovieEntity>>
}