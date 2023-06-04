package ru.riders.sportfinder.screen.auth_screens.authorization_screen

data class AuthorizationState(
    val login: String = "",
    val password: String = "",
    val errorMessage: String = "",
    val isLoading: Boolean = false
)
