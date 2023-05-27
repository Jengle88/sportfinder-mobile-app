package ru.riders.sportfinder.screen.watch_running_track_screen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraPosition
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import ru.riders.sportfinder.common.ApiResultState
import ru.riders.sportfinder.common.Constants
import ru.riders.sportfinder.domain.model.running_track.RunningTrack
import ru.riders.sportfinder.domain.use_case.LoadRunningTrack
import javax.inject.Inject

@HiltViewModel
class WatchRunningTracksViewModel @Inject constructor(
    private val loadRunningTrack: LoadRunningTrack,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val runningTrackId = savedStateHandle.get<Int>("trackInfoNumber")
    val centerSPbPoint = Constants.SPB_CENTER_POINT

    private val _runningTrack = mutableStateOf<RunningTrack?>(null)
    val runningTrack: State<RunningTrack?> = _runningTrack

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
                        _runningTrack.value = result.data
                    }
                }
            }.launchIn(viewModelScope)
        }
    }
    fun getOptimalCameraPosition(): CameraPosition {
        val runningTrackPoints = runningTrack.value?.points ?: throw IllegalStateException("runningTrack is null")

        val averageLatitude = runningTrackPoints.sumOf { it.latitude } / runningTrackPoints.size
        val averageLongitude = runningTrackPoints.sumOf { it.longitude } / runningTrackPoints.size

        return CameraPosition(Point(averageLatitude, averageLongitude), 10.0f, 0f, 0f)
    }
}