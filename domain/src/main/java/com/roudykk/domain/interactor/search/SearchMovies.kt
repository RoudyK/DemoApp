package com.roudykk.domain.interactor.search

import com.roudykk.domain.executor.PostExecutionThread
import com.roudykk.domain.interactor.ObservableUseCase
import com.roudykk.domain.model.Movie
import com.roudykk.domain.repository.MoviesRepository
import io.reactivex.Observable
import javax.inject.Inject

class SearchMovies @Inject constructor(
        postExecutionThread: PostExecutionThread,
        private val moviesRepository: MoviesRepository
) : ObservableUseCase<List<Movie>, SearchMovies.Params>(postExecutionThread) {

    override fun buildUseCase(params: Params?): Observable<List<Movie>> {
        if (params == null) throw IllegalArgumentException("Params cannot be null")
        return this.moviesRepository.searchMovies(params.searchQuery)
    }

    class Params(val searchQuery: String)
}