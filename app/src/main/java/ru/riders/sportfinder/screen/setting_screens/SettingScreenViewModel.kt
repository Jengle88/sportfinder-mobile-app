package ru.riders.sportfinder.screen.setting_screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import ru.riders.sportfinder.domain.use_case.LogoutUserProfile
import javax.inject.Inject

@HiltViewModel
class SettingScreenViewModel @Inject constructor(
    private val logoutUserProfile: LogoutUserProfile
): ViewModel() {

    private val _logoutState = MutableSharedFlow<Boolean>()
    val logoutState = _logoutState.asSharedFlow()

    fun logoutUser() {
        logoutUserProfile().onEach {
            _logoutState.emit(it)
        }.launchIn(viewModelScope)
    }
}