package ru.riders.sportfinder.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.Retrofit.Builder
import retrofit2.converter.gson.GsonConverterFactory
import ru.riders.sportfinder.common.Constants.API_URL_SERVER
import ru.riders.sportfinder.common.userProfileDataStore
import ru.riders.sportfinder.data.db.UserProfileDao
import ru.riders.sportfinder.data.db.UserProfileDaoImpl
import ru.riders.sportfinder.data.remote.ServerApi
import ru.riders.sportfinder.network.interceptor.TokenInterceptor
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideTokenInterceptor(
        userProfileDao: UserProfileDao
    ) = TokenInterceptor().apply {
        CoroutineScope(Dispatchers.Default).launch {
            updateToken(userProfileDao.getUserToken())
        }
    }

    @Provides
    @Singleton
    fun providePreferencesUserProfileDataStore(
        @ApplicationContext context: Context
    ): DataStore<Preferences> = context.userProfileDataStore

    @Provides
    @Singleton
    fun provideUserProfileDao(
        datastore: DataStore<Preferences>
    ): UserProfileDao = UserProfileDaoImpl(datastore)

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
}
