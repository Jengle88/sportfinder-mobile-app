package ru.riders.sportfinder.domain.repository

import ru.riders.sportfinder.data.remote.dto.SignInDto
import ru.riders.sportfinder.data.remote.dto.SignUpDto
import ru.riders.sportfinder.data.remote.dto.UserProfileDto

interface UserProfileRepository {

    suspend fun signUpUser(login: String, password: String): SignUpDto

    suspend fun signInUser(login: String, password: String): SignInDto

    suspend fun getUserInfo(userId: String): UserProfileDto
}