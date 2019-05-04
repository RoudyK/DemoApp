package com.roudykk.domain.interactor.browse

import com.roudykk.domain.executor.PostExecutionThread
import com.roudykk.domain.interactor.ObservableUseCase
import com.roudykk.domain.model.Movie
import com.roudykk.domain.repository.MoviesRepository
import io.reactivex.Observable
import javax.inject.Inject

class GetMovieDetails @Inject constructor(
        postExecutionThread: PostExecutionThread,
        private val moviesRepository: MoviesRepository
) : ObservableUseCase<Movie, GetMovieDetails.Params>(postExecutionThread) {

    override fun buildUseCase(params: Params?): Observable<Movie> {
        if (params == null) throw IllegalArgumentException("Params cannot be null")
        return this.moviesRepository.getMovieDetails(params.movieId)
    }

    class Params(val movieId: Int)
}