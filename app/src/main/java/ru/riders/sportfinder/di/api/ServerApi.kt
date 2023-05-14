package ru.riders.sportfinder.di.api

import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query
import ru.riders.sportfinder.data.SportCourtsResponse
import ru.riders.sportfinder.data.TrackInfoListResponse
import ru.riders.sportfinder.data.networkData.ProfileResponse
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

    @GET("user/{id}")
    fun getUserProfile(
        @Path("id") id: String
    ): Deferred<Response<ProfileResponse>>

    @GET("get_all_running_routes")
    fun getRunningTracks(): Deferred<Response<TrackInfoListResponse>>
}