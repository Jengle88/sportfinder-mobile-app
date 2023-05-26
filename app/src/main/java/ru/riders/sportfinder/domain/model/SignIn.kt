package ru.riders.sportfinder.domain.model

data class SignIn(
    val id: Int,
    val token: String
)

fun SignIn.toSignUp() =
    SignUp(
        id = id,
        token = token
    )