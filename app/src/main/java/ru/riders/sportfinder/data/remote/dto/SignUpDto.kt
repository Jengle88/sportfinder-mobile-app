package ru.riders.sportfinder.data.remote.dto

import ru.riders.sportfinder.domain.model.SignUp

data class SignUpDto(
    val id: Int,
    val token: String
)

fun SignUpDto.toSignUp() =
    SignUp(
        id = id,
        token = token
    )
