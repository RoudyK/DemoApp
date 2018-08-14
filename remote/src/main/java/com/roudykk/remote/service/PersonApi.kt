package com.roudykk.remote.service

import com.roudykk.remote.model.PersonModel
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

interface PersonApi {

    @GET("person/{personId}?append_to_response=credits")
    fun getPersonDetails(@Path("personId") personId: Int): Observable<PersonModel>

}