package ru.riders.sportfinder.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface UserProfileDao {

    @Query("SELECT * FROM user_token ORDER BY id DESC LIMIT 1")
    suspend fun getUserToken(): UserTokenEntity

    @Upsert
    suspend fun insertUserProfile(userToken: UserTokenEntity)

    @Delete
    suspend fun deleteUserProfile(userToken: UserTokenEntity)
}