package ru.riders.sportfinder.domain.repository

import ru.riders.sportfinder.data.remote.dto.AuthDto
import ru.riders.sportfinder.data.remote.dto.UserProfileDto

interface UserProfileRepository {

    suspend fun signUpUser(login: String, password: String): AuthDto

    suspend fun signInUser(login: String, password: String): AuthDto

    suspend fun getUserInfo(): UserProfileDto

    suspend fun checkTokenValidity(): Boolean
}