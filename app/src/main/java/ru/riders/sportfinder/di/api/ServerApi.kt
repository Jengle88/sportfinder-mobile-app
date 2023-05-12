package ru.riders.sportfinder.di.api

import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import ru.riders.sportfinder.data.SignInResponse
import ru.riders.sportfinder.data.SignUpRequestBody
import ru.riders.sportfinder.data.SignUpResponse

interface ServerApi {

    @POST("reg")
    fun signUp(
        @Body signUpRequestBody: SignUpRequestBody
    ): Deferred<Response<SignUpResponse>>

    @GET("login")
    fun signIn(
        @Query("login") login: String,
        @Query("password") password: String,
    ): Deferred<Response<SignInResponse>>

}