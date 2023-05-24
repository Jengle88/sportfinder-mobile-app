package ru.riders.sportfinder.di

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.Retrofit.Builder
import retrofit2.converter.gson.GsonConverterFactory
import ru.riders.sportfinder.common.Constants.API_URL_SERVER
import ru.riders.sportfinder.common.Constants.API_URL_YANDEXWEATHER
import ru.riders.sportfinder.data.remote.ServerApi
import ru.riders.sportfinder.data.remote.YandexWeatherApi
import ru.riders.sportfinder.data.repository.RunningTracksRepositoryImpl
import ru.riders.sportfinder.data.repository.SportCourtsRepositoryImpl
import ru.riders.sportfinder.data.repository.UserProfileRepositoryImpl
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

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

    @Provides
    @Singleton
    fun provideUserProfileRepository(serverApi: ServerApi) =
        UserProfileRepositoryImpl(serverApi)

    @Provides
    @Singleton
    fun provideSportCourtsRepository(serverApi: ServerApi) =
        SportCourtsRepositoryImpl(serverApi)

    @Provides
    @Singleton
    fun provideRunningTracksRepository(serverApi: ServerApi) =
        RunningTracksRepositoryImpl(serverApi)
}