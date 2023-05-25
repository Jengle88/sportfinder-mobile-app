package ru.riders.sportfinder.data.remote.dto

import ru.riders.sportfinder.domain.model.SignIn

data class SignInDto(
    val id: Int,
    val token: String
)

fun SignInDto.toSignIn() =
    SignIn(
        id = id,
        token = token
    )
