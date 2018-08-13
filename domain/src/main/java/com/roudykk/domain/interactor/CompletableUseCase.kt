package com.roudykk.domain.interactor

import com.roudykk.domain.executor.PostExecutionThread
import io.reactivex.Completable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableCompletableObserver
import io.reactivex.schedulers.Schedulers

abstract class CompletableUseCase<Params>(private var thread: PostExecutionThread) {
    private var disposables = CompositeDisposable()

    abstract fun buildUseCase(params: Params?): Completable

    fun execute(observer: DisposableCompletableObserver, params: Params?) {
        val observable = this.buildUseCase(params)
                .subscribeOn(Schedulers.io())
                .observeOn(thread.scheduler)
        this.disposables.addAll(observable.subscribeWith(observer))
    }

    fun dispose() {
        this.disposables.dispose()
    }
}