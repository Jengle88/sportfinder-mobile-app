package ru.riders.sportfinder.screen.watch_running_track_screen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.riders.sportfinder.common.Constants
import ru.riders.sportfinder.domain.model.running_track.RunningTrack
import javax.inject.Inject

@HiltViewModel
class WatchRunningTracksViewModel @Inject constructor(

) : ViewModel() {
    val centerSPbPoint = Constants.SPB_CENTER_POINT


    private val _runningTrack = mutableStateOf<RunningTrack?>(null)
    val runningTrack: State<RunningTrack?> = _runningTrack


}