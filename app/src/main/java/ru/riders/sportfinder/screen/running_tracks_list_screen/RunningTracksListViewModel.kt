package ru.riders.sportfinder.screen.running_tracks_list_screen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import ru.riders.sportfinder.common.ApiResultState
import ru.riders.sportfinder.domain.model.running_track.RunningTrackForList
import ru.riders.sportfinder.domain.model.running_track.toRunningTrackForList
import ru.riders.sportfinder.domain.use_case.LoadRunningTracksList
import javax.inject.Inject

@HiltViewModel
class RunningTracksListViewModel @Inject constructor(
    private val loadRunningTracksList: LoadRunningTracksList
): ViewModel() {

    private val _listRunningTracks = mutableStateOf(emptyList<RunningTrackForList>())
    val listRunningTracks: State<List<RunningTrackForList>> = _listRunningTracks

    init {
        updateListRunningTracks()
    }

    private fun updateListRunningTracks() {
        loadRunningTracksList.invoke().onEach { result ->
            when (result) {
                is ApiResultState.Loading -> {

                }

                is ApiResultState.Error -> {

                }

                is ApiResultState.Success -> {
                    _listRunningTracks.value = (result.data ?: emptyList()).map { it.toRunningTrackForList() }
                }
            }
        }.launchIn(viewModelScope)
    }
}