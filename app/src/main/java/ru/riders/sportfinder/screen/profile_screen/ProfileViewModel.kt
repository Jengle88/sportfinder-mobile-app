package ru.riders.sportfinder.screen.profile_screen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import ru.riders.sportfinder.common.ApiResultState
import ru.riders.sportfinder.domain.use_case.GetUserProfile
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getUserProfile: GetUserProfile
): ViewModel() {

    private val _state = mutableStateOf(ProfileState())
    val state: State<ProfileState> = _state

    init {
        updateProfileState()
    }

    fun switchTab(newTabIndex: Int) {
        _state.value = _state.value.copy(
            tabIndex = newTabIndex
        )
    }

    fun updateProfileState() {
        getUserProfile().onEach { result ->
            when(result) {
                is ApiResultState.Loading -> {

                }
                is ApiResultState.Error -> {

                }
                is ApiResultState.Success -> {
                    _state.value = _state.value.copy(
                        profileName = result.data?.userName ?: "Name Placeholder"
                    )
                }
            }

        }.launchIn(viewModelScope)
    }
}