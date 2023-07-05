package ru.riders.sportfinder.data.db

interface UserProfileDao {

    suspend fun getUserToken(): String?

    suspend fun insertUserProfile(userToken: String)

    suspend fun deleteUserProfile()
}