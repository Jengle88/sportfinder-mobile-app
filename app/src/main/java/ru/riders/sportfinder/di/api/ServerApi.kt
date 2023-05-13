package ru.riders.sportfinder.di.api

import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import ru.riders.sportfinder.data.SportCourtsResponse
import ru.riders.sportfinder.data.networkData.SignInResponse
import ru.riders.sportfinder.data.networkData.SignUpRequestBody
import ru.riders.sportfinder.data.networkData.SignUpResponse

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

    @GET("spot")
    fun getSportSpots(): Deferred<Response<SportCourtsResponse>>

}