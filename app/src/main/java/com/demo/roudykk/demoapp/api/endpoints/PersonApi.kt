package com.demo.roudykk.demoapp.api.endpoints

import com.demo.roudykk.demoapp.api.models.Person
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

interface PersonApi {

    @GET("person/{personId}?append_to_response=credits")
    fun getPersonDetails(@Path("personId") personId: Int): Observable<Person>

}