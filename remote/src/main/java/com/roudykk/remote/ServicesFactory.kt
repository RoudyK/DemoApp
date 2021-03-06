package com.roudykk.remote

import com.google.gson.Gson
import com.roudykk.remote.service.DiscoverApi
import com.roudykk.remote.service.MovieApi
import com.roudykk.remote.service.PersonApi
import com.roudykk.remote.service.SearchApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object ServicesFactory {
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

        authBuilder.addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.HEADERS))

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

    fun discoverApi(): DiscoverApi {
        return builder.build().create(DiscoverApi::class.java)
    }

    fun movieApi(): MovieApi {
        return builder.build().create(MovieApi::class.java)
    }

    fun searchApi(): SearchApi {
        return builder.build().create(SearchApi::class.java)
    }

    fun personApi(): PersonApi {
        return builder.build().create(PersonApi::class.java)
    }
}