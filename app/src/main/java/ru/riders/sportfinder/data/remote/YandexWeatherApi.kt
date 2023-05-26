package ru.riders.sportfinder.data.remote

import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface YandexWeatherApi {
    @GET("informers?")
    @Headers("X-Yandex-API-Key:2e2e4eec-fbcf-4310-8329-1f945e5dc1ba")
    fun getWeather(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double
    ): Deferred<Response<WeatherResponse>>

    data class WeatherResponse(val str: String)
}