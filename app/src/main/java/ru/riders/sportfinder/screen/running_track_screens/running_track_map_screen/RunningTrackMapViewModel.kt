package ru.riders.sportfinder.screen.running_track_screens.running_track_map_screen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import ru.riders.sportfinder.common.ApiResultState
import ru.riders.sportfinder.domain.model.running_track.RunningTrackVO
import ru.riders.sportfinder.domain.use_case.LoadRunningTrack
import javax.inject.Inject

@HiltViewModel
class RunningTrackMapViewModel @Inject constructor(
    private val loadRunningTrack: LoadRunningTrack,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val runningTrackId = savedStateHandle.get<Int>("trackInfoNumber")

    private val _runningTrackVO = mutableStateOf<RunningTrackVO?>(null)
    val runningTrackVO: State<RunningTrackVO?> = _runningTrackVO

    init {
        updateTrack()
    }

    fun updateTrack() {
        runningTrackId?.let {
            loadRunningTrack(it).onEach { result ->
                when (result) {
                    is ApiResultState.Loading -> {

                    }
                    is ApiResultState.Error -> {

                    }
                    is ApiResultState.Success -> {
                        _runningTrackVO.value = result.data
                    }
                }
            }.launchIn(viewModelScope)
        }
    }
}