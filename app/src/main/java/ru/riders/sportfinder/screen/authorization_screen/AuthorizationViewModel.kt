package ru.riders.sportfinder.screen.authorization_screen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import ru.riders.sportfinder.common.ApiResultState
import ru.riders.sportfinder.domain.use_case.SignInUser
import javax.inject.Inject

@HiltViewModel
class AuthorizationViewModel @Inject constructor(
    private val signInUser: SignInUser
): ViewModel() {

    private val isAdminMode = true

    private val _state = mutableStateOf(AuthorizationState())
    val state: State<AuthorizationState> = _state

    fun updateLogin(newLogin: String) {
        _state.value = _state.value.copy(login = newLogin)
    }
    fun updatePassword(newPassword: String) {
        _state.value = _state.value.copy(password = newPassword)
    }

    fun trySignInUser(
        onSuccess: () -> Unit
    ) {
        val login = if(isAdminMode) "admin" else state.value.login
        val password = if(isAdminMode) "admin" else state.value.password
        signInUser(login, password).onEach { result ->
            when (result) {
                is ApiResultState.Success -> {
                    onSuccess()
                }
                is ApiResultState.Error -> {
                    _state.value = _state.value.copy(
                        errorMessage = result.message ?: "Api result error",
                        isLoading = false
                    )
                }
                is ApiResultState.Loading -> {
                    _state.value = _state.value.copy(
                        errorMessage = "",
                        isLoading = true
                    )
                }
            }
        }.launchIn(viewModelScope)
    }
}