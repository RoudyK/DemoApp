package com.roudykk.domain.interactor.watchlist

import com.roudykk.domain.executor.PostExecutionThread
import com.roudykk.domain.interactor.CompletableUseCase
import com.roudykk.domain.repository.MoviesRepository
import io.reactivex.Completable
import javax.inject.Inject

class ClearWatchListMovies @Inject constructor(
        postExecutionThread: PostExecutionThread,
        private val moviesRepository: MoviesRepository
) : CompletableUseCase<Nothing>(postExecutionThread) {

    override fun buildUseCase(params: Nothing?): Completable {
        return this.moviesRepository.clearWatchListMovies()
    }

}