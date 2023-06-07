package ru.riders.sportfinder.data.remote.dto

import ru.riders.sportfinder.data.db.UserTokenEntity

data class AuthDto(
    val token: String
)

fun AuthDto.toUserTokenEntity() =
    UserTokenEntity(
        token = token
    )