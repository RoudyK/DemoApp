package com.roudykk.domain.interactor.watchlist

import com.roudykk.domain.executor.PostExecutionThread
import com.roudykk.domain.interactor.CompletableUseCase
import com.roudykk.domain.model.Movie
import com.roudykk.domain.repository.MoviesRepository
import io.reactivex.Completable
import javax.inject.Inject

class AddMovieWatchList @Inject constructor(
        postExecutionThread: PostExecutionThread,
        private val moviesRepository: MoviesRepository
) : CompletableUseCase<AddMovieWatchList.Params>(postExecutionThread) {

    override fun buildUseCase(params: Params?): Completable {
        if (params == null) throw IllegalArgumentException("Params cannot be null")
        return this.moviesRepository.addMovieWatchList(params.movie)
    }

    data class Params(val movie: Movie)
}