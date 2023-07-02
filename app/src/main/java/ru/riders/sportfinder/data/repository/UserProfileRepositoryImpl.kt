package ru.riders.sportfinder.data.repository

import okhttp3.Credentials
import ru.riders.sportfinder.common.utils.NetworkUtils
import ru.riders.sportfinder.data.db.UserProfileDao
import ru.riders.sportfinder.data.remote.ServerApi
import ru.riders.sportfinder.data.remote.dto.AuthDto
import ru.riders.sportfinder.data.remote.dto.UserProfileDto
import ru.riders.sportfinder.data.remote.dto.toUserTokenEntity
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
        userProfileDao.insertUserProfile(signUpData.toUserTokenEntity())
        return signUpData
    }

    override suspend fun signInUser(
        login: String,
        password: String
    ): AuthDto {
        val signInData = serverApi.signIn(Credentials.basic(login, password))
        userProfileDao.insertUserProfile(signInData.toUserTokenEntity())
        return signInData
    }

    override suspend fun logoutUser(): Boolean {
        val currToken = userProfileDao.getUserToken() ?: return false// null может возникать, если таблица пустая
        userProfileDao.deleteUserProfile(currToken)
        return true
    }

    override suspend fun getUserInfo(): UserProfileDto {
        val userToken = userProfileDao.getUserToken().token
        return serverApi.getUserProfile(userToken)
    }

    override suspend fun checkTokenValidity(): Boolean {
        val currToken = userProfileDao.getUserToken()?.token ?: return false // null может возникать, если таблица пустая
        val serverToken = serverApi.refreshToken(NetworkUtils.asBearerHeader(currToken)).token
        return serverToken.isNotEmpty()
    }
}