package ru.riders.sportfinder.data.remote

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query
import ru.riders.sportfinder.data.remote.dto.AuthDto
import ru.riders.sportfinder.data.remote.dto.CreateRunningTrackDto
import ru.riders.sportfinder.data.remote.dto.RunningTrackDto
import ru.riders.sportfinder.data.remote.dto.RunningTracksDto
import ru.riders.sportfinder.data.remote.dto.SportCourtsDto
import ru.riders.sportfinder.data.remote.dto.UserProfileDto
import ru.riders.sportfinder.data.remote.request_body.CreateRunningTrackBody
import ru.riders.sportfinder.data.remote.request_body.SignUpRequestBody
import ru.riders.sportfinder.network.interceptor.WithUserToken

interface ServerApi {

    @POST("reg")
    suspend fun signUp(
        @Body signUpRequestBody: SignUpRequestBody
    ): AuthDto

    @GET("login")
    suspend fun signIn(
        @Header("Authorization") credentials: String
    ): AuthDto

    @GET("token")
    @WithUserToken
    suspend fun refreshToken(): AuthDto

    @GET("spot")
    suspend fun getSportCourts(): SportCourtsDto

    @GET("user/{id}")
    suspend fun getUserProfile(
        @Path("id") userId: String
    ): UserProfileDto

    @GET("get_all_running_routes")
    suspend fun getRunningTracks(): RunningTracksDto

    @GET("get_running_route")
    suspend fun getRunningTrack(
        @Query("id") id: Int
    ): RunningTrackDto

    @POST("create_running_route")
    @WithUserToken
    suspend fun createRunningTrack(
        @Body createRunningTrackBody: CreateRunningTrackBody
    ): CreateRunningTrackDto
}