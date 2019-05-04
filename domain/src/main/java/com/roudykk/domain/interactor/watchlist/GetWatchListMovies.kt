package com.roudykk.domain.interactor.watchlist

import com.roudykk.domain.executor.PostExecutionThread
import com.roudykk.domain.interactor.ObservableUseCase
import com.roudykk.domain.model.Movie
import com.roudykk.domain.repository.MoviesRepository
import io.reactivex.Observable
import javax.inject.Inject

class GetWatchListMovies @Inject constructor(
        postExecutionThread: PostExecutionThread,
        private val moviesRepository: MoviesRepository
) : ObservableUseCase<List<Movie>, Nothing>(postExecutionThread) {

    override fun buildUseCase(params: Nothing?): Observable<List<Movie>> {
        return this.moviesRepository.getWatchListMovies()
    }
}