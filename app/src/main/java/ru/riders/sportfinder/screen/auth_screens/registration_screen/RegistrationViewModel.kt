package ru.riders.sportfinder.screen.auth_screens.registration_screen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import ru.riders.sportfinder.common.ApiResultState
import ru.riders.sportfinder.domain.use_case.SignUpUser
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor(
    private val signUpUser: SignUpUser
): ViewModel() {

    private val _state = mutableStateOf(RegistrationState())
    val state: State<RegistrationState> = _state

    fun updateLogin(newLogin: String) {
        _state.value = _state.value.copy(login = newLogin)
    }
    fun updatePassword(newPassword: String) {
        _state.value = _state.value.copy(password = newPassword)
    }

    fun trySignUpUser(
        onSuccess: () -> Unit
    ) {
        signUpUser(state.value.login, state.value.password).onEach { result ->
            when (result) {
                is ApiResultState.Success -> {
                    if (result.data == true)
                        onSuccess()
                    else
                        _state.value = _state.value.copy(
                            errorMessage = result.message ?: "Api result error",
                            isLoading = false
                        )
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