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
import ru.riders.sportfinder.common.Constants.API_URL_YANDEXWEATHER
import ru.riders.sportfinder.data.db.SportFinderDatabase
import ru.riders.sportfinder.data.db.UserProfileDao
import ru.riders.sportfinder.data.remote.ServerApi
import ru.riders.sportfinder.data.remote.YandexWeatherApi
import ru.riders.sportfinder.data.repository.RunningTracksRepositoryImpl
import ru.riders.sportfinder.data.repository.SportCourtsListRepositoryImpl
import ru.riders.sportfinder.data.repository.UserProfileRepositoryImpl
import ru.riders.sportfinder.domain.repository.RunningTracksRepository
import ru.riders.sportfinder.domain.repository.SportCourtsListRepository
import ru.riders.sportfinder.domain.repository.UserProfileRepository
import ru.riders.sportfinder.domain.use_case.GetUserProfile
import ru.riders.sportfinder.domain.use_case.LoadRunningTrack
import ru.riders.sportfinder.domain.use_case.LoadRunningTracksList
import ru.riders.sportfinder.domain.use_case.LoadSportCourtsList
import ru.riders.sportfinder.domain.use_case.SignInUser
import ru.riders.sportfinder.domain.use_case.SignUpUser
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRetrofitBuilder() = Retrofit.Builder()
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .addConverterFactory(GsonConverterFactory.create())
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

    @Provides
    @Singleton
    fun provideUserProfileRepository(serverApi: ServerApi): UserProfileRepository =
        UserProfileRepositoryImpl(serverApi)

    @Provides
    @Singleton
    fun provideSportCourtListRepository(serverApi: ServerApi): SportCourtsListRepository =
        SportCourtsListRepositoryImpl(serverApi)

    @Provides
    @Singleton
    fun provideRunningTracksRepository(serverApi: ServerApi): RunningTracksRepository =
        RunningTracksRepositoryImpl(serverApi)

    @Provides
    @Singleton
    fun provideSignUpUser(
        userProfileDao: UserProfileDao,
        userProfileRepository: UserProfileRepository
    ) = SignUpUser(userProfileDao, userProfileRepository)

    @Provides
    @Singleton
    fun provideSignInUser(
        userProfileDao: UserProfileDao,
        userProfileRepository: UserProfileRepository
    ) = SignInUser(userProfileDao, userProfileRepository)

    @Provides
    @Singleton
    fun provideGetUserProfile(
        userProfileDao: UserProfileDao,
        userProfileRepository: UserProfileRepository
    ) = GetUserProfile(userProfileDao, userProfileRepository)

    @Provides
    @Singleton
    fun provideLoadSportCourtsList(
        sportCourtsListRepository: SportCourtsListRepository
    ) = LoadSportCourtsList(sportCourtsListRepository)

    @Provides
    @Singleton
    fun provideLoadRunningTracksList(
        runningTracksRepository: RunningTracksRepository
    ) = LoadRunningTracksList(runningTracksRepository)

    @Provides
    @Singleton
    fun provideLoadRunningTrack(
        runningTracksRepository: RunningTracksRepository
    ) = LoadRunningTrack(runningTracksRepository)
}