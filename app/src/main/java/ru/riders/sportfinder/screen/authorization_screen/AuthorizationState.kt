package ru.riders.sportfinder.screen.authorization_screen

data class AuthorizationState(
    val login: String = "",
    val password: String = "",
    val errorMessage: String = "",
    val isLoading: Boolean = false
)
