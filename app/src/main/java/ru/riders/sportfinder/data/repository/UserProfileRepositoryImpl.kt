package ru.riders.sportfinder.data.repository

import okhttp3.Credentials
import ru.riders.sportfinder.data.remote.ServerApi
import ru.riders.sportfinder.data.remote.dto.AuthDto
import ru.riders.sportfinder.data.remote.dto.SignUpRequestBody
import ru.riders.sportfinder.data.remote.dto.UserProfileDto
import ru.riders.sportfinder.domain.repository.UserProfileRepository
import javax.inject.Inject

class UserProfileRepositoryImpl @Inject constructor(
    private val serverApi: ServerApi
) : UserProfileRepository {
    override suspend fun signUpUser(
        login: String,
        password: String
    ): AuthDto {
        return serverApi.signUp(SignUpRequestBody(login, password))
    }

    override suspend fun signInUser(
        login: String,
        password: String
    ): AuthDto {
        return serverApi.signIn(Credentials.basic(login, password))
    }

    override suspend fun getUserInfo(userId: String): UserProfileDto {
        return serverApi.getUserProfile(userId)
    }
}