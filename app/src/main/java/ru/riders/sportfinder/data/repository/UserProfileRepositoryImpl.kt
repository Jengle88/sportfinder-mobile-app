package ru.riders.sportfinder.data.repository

import ru.riders.sportfinder.data.remote.ServerApi
import ru.riders.sportfinder.data.remote.dto.SignInDto
import ru.riders.sportfinder.data.remote.dto.SignUpDto
import ru.riders.sportfinder.data.remote.dto.SignUpRequestBody
import ru.riders.sportfinder.data.remote.dto.UserProfileDto
import ru.riders.sportfinder.domain.repository.UserProfileRepository
import javax.inject.Inject

class UserProfileRepositoryImpl @Inject constructor(
    private val api: ServerApi
) : UserProfileRepository {
    override suspend fun singUpUser(
        login: String,
        password: String
    ): SignUpDto {
        return api.signUp(SignUpRequestBody(login, password))
    }

    override suspend fun singInUser(
        login: String,
        password: String
    ): SignInDto {
        return api.signIn(login, password)
    }

    override suspend fun getUserInfo(userId: String): UserProfileDto {
        return api.getUserProfile(userId)
    }
}