package ru.riders.sportfinder.screen.running_track_screens.create_running_track_screen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraPosition
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import ru.riders.sportfinder.common.ApiResultState
import ru.riders.sportfinder.common.Constants
import ru.riders.sportfinder.common.utils.MapTools
import ru.riders.sportfinder.domain.model.TagVO
import ru.riders.sportfinder.domain.use_case.GetTags
import ru.riders.sportfinder.screen.common_components.JCMapView
import javax.inject.Inject

@HiltViewModel
class CreateRunningTrackViewModel @Inject constructor(
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
}