package com.roudykk.domain.interactor.watchlist

import com.roudykk.domain.executor.PostExecutionThread
import com.roudykk.domain.interactor.CompletableUseCase
import com.roudykk.domain.repository.MoviesRepository
import io.reactivex.Completable
import javax.inject.Inject

class RemoveMovieWatchList @Inject constructor(
        postExecutionThread: PostExecutionThread,
        private val moviesRepository: MoviesRepository
) : CompletableUseCase<RemoveMovieWatchList.Params>(postExecutionThread) {

    override fun buildUseCase(params: RemoveMovieWatchList.Params?): Completable {
        if (params == null) throw IllegalArgumentException("Params cannot be null")
        return this.moviesRepository.removeMovieWatchList(params.movieId)
    }

    class Params(val movieId: Int)
}