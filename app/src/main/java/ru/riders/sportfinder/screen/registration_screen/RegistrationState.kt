package ru.riders.sportfinder.screen.registration_screen

data class RegistrationState(
    val login: String = "",
    val password: String = "",
    val errorMessage: String = "",
    val isLoading: Boolean = false
)
