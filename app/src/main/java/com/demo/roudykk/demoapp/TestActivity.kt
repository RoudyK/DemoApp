package com.demo.roudykk.demoapp

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.demo.roudykk.demoapp.api.executor.ApiExecutor
import com.demo.roudykk.demoapp.util.extensions.initThreads

class TestActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val api = intent.getSerializableExtra("api") as ApiExecutor

        api.getMovies(1)
                .initThreads()
                .doOnError { error ->
                    Log.d("awd", error.toString())
                }
                .doOnNext { result ->
                    Log.d("awd", result.toString())
                }
                .subscribe()
    }
}