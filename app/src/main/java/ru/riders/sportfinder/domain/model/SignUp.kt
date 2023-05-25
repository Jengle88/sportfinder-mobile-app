package ru.riders.sportfinder.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class SignUp(
    @PrimaryKey val id: Int,
    val token: String
)
