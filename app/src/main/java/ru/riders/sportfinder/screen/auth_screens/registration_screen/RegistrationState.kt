package ru.riders.sportfinder.screen.auth_screens.registration_screen

data class RegistrationState(
    val login: String = "",
    val password: String = "",
    val errorMessage: String = "",
    val isLoading: Boolean = false
)
