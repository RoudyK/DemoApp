package com.roudykk.domain.interactor.browse

import com.roudykk.domain.executor.PostExecutionThread
import com.roudykk.domain.interactor.ObservableUseCase
import com.roudykk.domain.model.MovieGroup
import com.roudykk.domain.repository.MoviesRepository
import io.reactivex.Observable
import javax.inject.Inject

class GetMoviesGroups @Inject constructor(
        postExecutionThread: PostExecutionThread,
        private val moviesRepository: MoviesRepository
) : ObservableUseCase<List<MovieGroup>, Nothing>(postExecutionThread) {

    override fun buildUseCase(params: Nothing?): Observable<List<MovieGroup>> {
        return this.moviesRepository.getMovieGroups()
    }

}