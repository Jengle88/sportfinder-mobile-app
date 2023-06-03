package ru.riders.sportfinder.screen.create_running_track_screen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraPosition
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.riders.sportfinder.common.Constants
import ru.riders.sportfinder.common.utils.MapTools
import ru.riders.sportfinder.screen.common_components.JCMapView
import javax.inject.Inject

@HiltViewModel
class CreateRunningTrackViewModel @Inject constructor(

): ViewModel() {

    private val listOfPoints = mutableListOf<Point>()

    private val _distance = mutableStateOf(0.0)
    val distance: State<Double> = _distance

    var title = mutableStateOf("")

    fun initMapView(mapView: JCMapView): JCMapView {
        return mapView.apply {
            map.move(CameraPosition(Constants.SPB_CENTER_POINT, 15.0f, 0f, 0f))

            onMapTapAction = { map, point ->
                val matchedPoints = MapTools.findPointInList(point, listOfPoints)
                if (matchedPoints.isEmpty()) {
                    listOfPoints.add(point)
                    updateRunningTrack(mapView)
                }
            }
            onMapLongTapAction = { map, point ->
                val matchedPoints = MapTools.findPointInList(point, listOfPoints)
                if (matchedPoints.isNotEmpty()) {
                    listOfPoints.remove(matchedPoints.first())
                    updateRunningTrack(mapView)
                }
            }
        }
    }

    private fun updateRunningTrack(mapView: JCMapView) {
        mapView.clearDrownRunningTrack()
        _distance.value = MapTools.calcDistance(listOfPoints)
        mapView.drawRunningTrack(listOfPoints, true)
    }

}