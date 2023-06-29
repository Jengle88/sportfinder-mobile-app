package ru.riders.sportfinder.screen.running_track_screens.running_tracks_list_screen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import ru.riders.sportfinder.common.ApiResultState
import ru.riders.sportfinder.domain.model.running_track.RunningTrackVOForList
import ru.riders.sportfinder.domain.model.running_track.toRunningTrackForList
import ru.riders.sportfinder.domain.use_case.LoadRunningTracksList
import javax.inject.Inject

@HiltViewModel
class RunningTracksListViewModel @Inject constructor(
    private val loadRunningTracksList: LoadRunningTracksList
): ViewModel() {

    private val _listRunningTracks = mutableStateOf(emptyList<RunningTrackVOForList>())
    val listRunningTracks: State<List<RunningTrackVOForList>> = _listRunningTracks

    private var _isLoading = mutableStateOf(false)
    val isLoading = _isLoading as State<Boolean>

    init {
        updateListRunningTracks()
    }

    private fun updateListRunningTracks() {
        loadRunningTracksList.invoke().onEach { result ->
            when (result) {
                is ApiResultState.Loading -> {
                    _isLoading.value = true
                }

                is ApiResultState.Error -> {

                }

                is ApiResultState.Success -> {
                    _isLoading.value = false
                    _listRunningTracks.value = (result.data ?: emptyList()).map { it.toRunningTrackForList() }
                }
            }
        }.launchIn(viewModelScope)
    }
}
