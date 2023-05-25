package ru.riders.sportfinder.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.riders.sportfinder.domain.model.SignUp

@Dao
interface UserProfileDao {


    // TODO: В будущем убрать и заменить на получение token
    @Query("SELECT * FROM signup LIMIT 1")
    suspend fun getUserInfo(): SignUp

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUserProfile(signUp: SignUp)

    @Delete
    suspend fun deleteUserProfile(signUp: SignUp)
}