package ru.riders.sportfinder.screen.watch_running_track_screen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yandex.mapkit.map.CameraPosition
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import ru.riders.sportfinder.common.ApiResultState
import ru.riders.sportfinder.common.Constants
import ru.riders.sportfinder.common.utils.MapTools.getAveragePoint
import ru.riders.sportfinder.domain.model.running_track.RunningTrackVO
import ru.riders.sportfinder.domain.use_case.LoadRunningTrack
import ru.riders.sportfinder.screen.common_components.JCMapView
import javax.inject.Inject

@HiltViewModel
class WatchRunningTracksViewModel @Inject constructor(
    private val loadRunningTrack: LoadRunningTrack,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val runningTrackId = savedStateHandle.get<Int>("trackInfoNumber")

    private val _runningTrackVO = mutableStateOf<RunningTrackVO?>(null)
    val runningTrackVO: State<RunningTrackVO?> = _runningTrackVO

    init {
        updateTrack()
    }

    fun initMapView(mapView: JCMapView): JCMapView {
        return mapView.apply {
            map.move(CameraPosition(Constants.SPB_CENTER_POINT, 15.0f, 0f, 0f))
        }
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
    fun getOptimalCameraPosition(): CameraPosition {
        val runningTrackPoints = runningTrackVO.value?.points ?: throw IllegalStateException("runningTrack is null")

        return CameraPosition(getAveragePoint(runningTrackPoints), 10.0f, 0f, 0f)
    }
}