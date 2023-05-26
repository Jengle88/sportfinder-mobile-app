package ru.riders.sportfinder.data.remote

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query
import ru.riders.sportfinder.data.remote.dto.RunningTracksDto
import ru.riders.sportfinder.data.remote.dto.SignInDto
import ru.riders.sportfinder.data.remote.dto.SignUpDto
import ru.riders.sportfinder.data.remote.dto.SignUpRequestBody
import ru.riders.sportfinder.data.remote.dto.SportCourtsDto
import ru.riders.sportfinder.data.remote.dto.UserProfileDto

interface ServerApi {

    @POST("reg")
    suspend fun signUp(
        @Body signUpRequestBody: SignUpRequestBody
    ): SignUpDto

    @GET("login")
    suspend fun signIn(
        @Query("login") login: String,
        @Query("password") password: String,
    ): SignInDto

    @GET("spot")
    suspend fun getSportCourts(): SportCourtsDto

    @GET("user/{id}")
    suspend fun getUserProfile(
        @Path("id") userId: String
    ): UserProfileDto

    @GET("get_all_running_routes")
    suspend fun getRunningTracks(): RunningTracksDto
}