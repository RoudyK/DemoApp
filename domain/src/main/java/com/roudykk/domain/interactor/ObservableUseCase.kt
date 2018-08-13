package com.roudykk.domain.interactor

import com.roudykk.domain.executor.PostExecutionThread
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

abstract class ObservableUseCase<T, Params>(private var thread: PostExecutionThread) {
    private var disposables = CompositeDisposable()

    abstract fun buildUseCase(params: Params?): Observable<T>

    fun execute(observer: DisposableObserver<T>, params: Params?) {
        val observable = this.buildUseCase(params)
                .subscribeOn(Schedulers.io())
                .observeOn(thread.scheduler)
        this.disposables.addAll(observable.subscribeWith(observer))
    }

    fun dispose() {
        this.disposables.dispose()
    }
}