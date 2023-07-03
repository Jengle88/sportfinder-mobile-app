package ru.riders.sportfinder.di

import android.app.Application
import androidx.room.Room
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
import ru.riders.sportfinder.data.db.SportFinderDatabase
import ru.riders.sportfinder.data.remote.ServerApi
import ru.riders.sportfinder.network.interceptor.TokenInterceptor
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideTokenInterceptor() = TokenInterceptor()

    @Provides
    @Singleton
    fun provideOkHttpClient(
        tokenInterceptor: TokenInterceptor
    ) = OkHttpClient().newBuilder()
        .readTimeout(10, TimeUnit.SECONDS)
        .writeTimeout(10, TimeUnit.SECONDS)
        .addInterceptor(tokenInterceptor)
        .build()

    @Provides
    @Singleton
    fun provideRetrofitBuilder(okHttpClient: OkHttpClient): Retrofit.Builder = Retrofit.Builder()
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)

    @Provides
    @Singleton
    fun provideServerApi(retrofitBuilder: Builder): ServerApi = retrofitBuilder
        .baseUrl(API_URL_SERVER)
        .build()
        .create(ServerApi::class.java)

    @Provides
    @Singleton
    fun provideSportFinderDatabase(app: Application) =
        Room.databaseBuilder(
            app,
            SportFinderDatabase::class.java,
            SportFinderDatabase.DATABASE_NAME
        ).build()

    @Provides
    @Singleton
    fun provideUserProfileDao(sportFinderDatabase: SportFinderDatabase) =
        sportFinderDatabase.userProfileDao
}
