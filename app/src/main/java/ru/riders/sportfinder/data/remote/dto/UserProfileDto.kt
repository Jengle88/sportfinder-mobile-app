package ru.riders.sportfinder.data.remote.dto

import ru.riders.sportfinder.domain.model.UserProfile

data class UserProfileDto(
    val id: String,
    val login: String
)

fun UserProfileDto.toUserProfile() = UserProfile(
    userName = login
)