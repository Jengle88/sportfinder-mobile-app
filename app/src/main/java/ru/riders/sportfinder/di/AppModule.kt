package ru.riders.sportfinder.di

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.yandex.mapkit.geometry.Point
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.Retrofit.Builder
import retrofit2.converter.gson.GsonConverterFactory
import ru.riders.sportfinder.di.api.ServerApi
import ru.riders.sportfinder.di.api.YandexWeatherApi
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    const val API_URL_YANDEXWEATHER = "https://api.weather.yandex.ru/v1/"
    const val API_URL_SERVER = "https://sportfinder-backend.vercel.app/api/"
    @Provides
    @Named("YANDEX_MAP_APIKEY")
    @Singleton
    fun provideYandexAPIMapKey() = "336e3861-b744-436a-a1f8-d48968b5ca21"


    @Provides
    @Named("SPB_CENTER_POINT")
    @Singleton
    fun provideSPbCenterPoint() = Point(59.935227, 30.329152)

    @Provides
    @Singleton
    fun provideRetrofitBuilder() = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .client(OkHttpClient().newBuilder()
            .readTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .build()
        )

    @Provides
    @Singleton
    fun provideYandexWeatherApi(retrofitBuilder: Retrofit.Builder) = retrofitBuilder
        .baseUrl(API_URL_YANDEXWEATHER)
        .build()
        .create(YandexWeatherApi::class.java)

    @Provides
    @Singleton
    fun provideServerApi(retrofitBuilder: Builder) = retrofitBuilder
        .baseUrl(API_URL_SERVER)
        .build()
        .create(ServerApi::class.java)
}