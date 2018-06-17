package com.demo.roudykk.demoapp.util.extensions

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

fun <T: Any> Observable<T>.initThreads() : Observable<T> {
    return subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
}