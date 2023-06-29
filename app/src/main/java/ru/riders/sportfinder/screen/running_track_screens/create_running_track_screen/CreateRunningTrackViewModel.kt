package ru.riders.sportfinder.screen.running_track_screens.create_running_track_screen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import ru.riders.sportfinder.common.ApiResultState
import ru.riders.sportfinder.common.utils.MapTools
import ru.riders.sportfinder.domain.model.TagVO
import ru.riders.sportfinder.domain.use_case.CreateRunningTrack
import ru.riders.sportfinder.domain.use_case.GetTags
import javax.inject.Inject

@HiltViewModel
class CreateRunningTrackViewModel @Inject constructor(
    private val createRunningTrack: CreateRunningTrack,
    getTags: GetTags
) : ViewModel() {

    val listOfPoints = mutableStateListOf<LatLng>()

    private val _distance = mutableStateOf(0.0)
    val distance: State<Double> = _distance

    var listOfTags = mutableStateOf<List<TagVO>>(emptyList())
    val listOfSelectedTags = mutableStateListOf<TagVO>() // id_tag, title_tag

    var title = mutableStateOf("")

    init {
        getTags().onEach { result ->
            when(result) {
                is ApiResultState.Success -> {
                    listOfTags.value = result.data ?: emptyList()
                }
                else -> {}
            }
        }.launchIn(viewModelScope)
    }

    fun addPoint(point: LatLng) {
        val matchedPoints =
            MapTools.findPointInList(point, listOfPoints)
        if (matchedPoints.isEmpty()) {
            listOfPoints.add(point)
        }
        _distance.value = MapTools.calcDistance(listOfPoints)
    }

    fun removePoint(point: LatLng) {
        val matchedPoints =
            MapTools.findPointInList(point, listOfPoints)
        if (matchedPoints.isNotEmpty()) {
            listOfPoints.remove(matchedPoints.first())
        }
        _distance.value = MapTools.calcDistance(listOfPoints)
    }

    fun addTagToList(tagVO: TagVO) {
        if (tagVO !in listOfSelectedTags) {
            listOfSelectedTags.add(tagVO)
        }
    }

    fun removeTagFromList(tagVO: TagVO) {
        if (tagVO in listOfSelectedTags) {
            listOfSelectedTags.remove(tagVO)
        }
    }

    fun saveRunningTrack(
        onSuccess: (Int) -> Unit,
        onFailure: () -> Unit
    ) {
        if (title.value.isNotEmpty() && listOfPoints.size >= 2) {
            createRunningTrack(
                title.value,
                distance.value,
                listOfPoints
            ).onEach { result ->
                when(result) {
                    is ApiResultState.Loading -> {

                    }
                    is ApiResultState.Error -> {

                    }
                    is ApiResultState.Success -> {
                        result.data?.let {
                            onSuccess(it)
                        }
                    }
                }
            }.launchIn(viewModelScope)
        } else {
            onFailure()
        }
    }
}