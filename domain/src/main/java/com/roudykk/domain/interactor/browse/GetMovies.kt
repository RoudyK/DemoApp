package com.roudykk.domain.interactor.browse

import com.roudykk.domain.executor.PostExecutionThread
import com.roudykk.domain.interactor.ObservableUseCase
import com.roudykk.domain.model.Movie
import com.roudykk.domain.repository.MoviesRepository
import io.reactivex.Observable
import javax.inject.Inject

class GetMovies @Inject constructor(
        postExecutionThread: PostExecutionThread,
        private val moviesRepository: MoviesRepository
) : ObservableUseCase<List<Movie>, GetMovies.Params>(postExecutionThread) {

    override fun buildUseCase(params: GetMovies.Params?): Observable<List<Movie>> {
        if (params == null) throw IllegalArgumentException("Params cannot be null")
        return this.moviesRepository.getMovies(params.index, params.page)
    }

    class Params(val index: String, val page: Int)
}