package ru.riders.sportfinder.data.repository

import android.util.Log
import okhttp3.Credentials
import ru.riders.sportfinder.data.db.UserProfileDao
import ru.riders.sportfinder.data.remote.ServerApi
import ru.riders.sportfinder.data.remote.dto.AuthDto
import ru.riders.sportfinder.data.remote.dto.UserProfileDto
import ru.riders.sportfinder.data.remote.request_body.SignUpRequestBody
import ru.riders.sportfinder.domain.repository.UserProfileRepository
import javax.inject.Inject

class UserProfileRepositoryImpl @Inject constructor(
    private val serverApi: ServerApi,
    private val userProfileDao: UserProfileDao
) : UserProfileRepository {
    override suspend fun signUpUser(
        login: String,
        password: String
    ): AuthDto {
        val signUpData = serverApi.signUp(SignUpRequestBody(login, password))
        userProfileDao.insertUserProfile(signUpData.token)
        return signUpData
    }

    override suspend fun signInUser(
        login: String,
        password: String
    ): AuthDto {
        val signInData = serverApi.signIn(Credentials.basic(login, password))
        userProfileDao.insertUserProfile(signInData.token)
        return signInData
    }

    override suspend fun logoutUser(): Boolean {
        userProfileDao.deleteUserProfile()
        return true
    }

    override suspend fun getUserInfo(): UserProfileDto {
        val userToken = userProfileDao.getUserToken()
        if (userToken == null) {
            Log.d("UserProfileRepositoryImpl", "userToken is null")
        }
        return serverApi.getUserProfile(userToken ?: "")
    }

    override suspend fun checkTokenValidity(): Boolean {
        val deviceToken = userProfileDao.getUserToken()
        val serverToken = if (deviceToken != null) serverApi.refreshToken().token else ""
        return serverToken == deviceToken
    }
}