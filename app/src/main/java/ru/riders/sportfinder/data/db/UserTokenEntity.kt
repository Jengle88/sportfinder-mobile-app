package ru.riders.sportfinder.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_token")
data class UserTokenEntity(
    val token: String,
    @PrimaryKey(autoGenerate = true) val id: Int = 0
)
