package ru.riders.sportfinder.di.api

import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET

interface ServerApi {

    @GET("helloworld")
    fun getData(): Deferred<Response<DBAnswer>>

    data class DBAnswer(val str: String)
}