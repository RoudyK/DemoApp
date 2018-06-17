package com.demo.roudykk.demoapp.api

import com.demo.roudykk.demoapp.api.endpoint.DiscoverApi
import com.google.gson.Gson
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


object Api {
    private const val BASE_URL = "https://api.themoviedb.org/3/"
    private const val API_KEY = "ff78853e064440dec46f90c5257b69da"

    private val authBuilder = OkHttpClient.Builder()
    private val builder: Retrofit.Builder = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(Gson()))

    init {
        if (authBuilder.interceptors() != null && !authBuilder.interceptors().isEmpty()) {
            authBuilder.interceptors().clear()
        }

        authBuilder.addInterceptor { chain ->
            val original = chain.request()
            val originalUrl = original.url()

            val newUrl = originalUrl.newBuilder()
                    .addQueryParameter("api_key", API_KEY)
                    .build()

            val requestBuilder = original.newBuilder()
                    .method(original.method(), original.body())
                    .header("Accept", "application/json")
                    .url(newUrl)

            val request = requestBuilder.build()
            chain.proceed(request)
        }

        builder.client(authBuilder.build())
    }

    fun discoverApi(): DiscoverApi{
        return builder.build().create(DiscoverApi::class.java)
    }
}