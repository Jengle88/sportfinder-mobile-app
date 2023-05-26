package ru.riders.sportfinder

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.PlacemarkMapObject
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.riders.sportfinder.common.Constants
import ru.riders.sportfinder.data.RunningTracksDto
import ru.riders.sportfinder.data.SportCourt
import ru.riders.sportfinder.data.remote.ServerApi
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(): ViewModel() {

    private var isFreeAccountMode = false

    @Inject
    lateinit var serverApi: ServerApi

    val centerSPbPoint: Point = Constants.SPB_CENTER_POINT

    val trackPoints = mutableStateListOf<PlacemarkMapObject>()

    var sportsCourts = mutableStateOf(emptyList<SportCourt>())

    var tracks = mutableStateOf(RunningTracksDto())

    fun loadRunningTracksList() {
        // TODO: Заменить на загрузку данных
/*        CoroutineScope(Dispatchers.Default).launch {
            try {
                val response = serverApi.getRunningTracks().await()
                val result = safeApiResult(response, "Error")
                when (result) {
                    is ApiResultState.Success -> {
                        tracks.value = result.data
                    }
                    is ApiResultState.Error -> {
                        val k = 3
                    }
                }
            } catch (e: Exception) {
                val k = 3
            }
        }*/
/*        tracks.value = mutableListOf<TrackInfo>().apply {
            repeat(10) { i ->
                add(TrackInfo(
                    "Title $i",
                    i*100.toDouble(),
                    i+10,
                    listOf("tag ${i+1}", "tag ${i+2}", "tag ${i+3}", "tag ${i+4}"),
                    listOf(Point(59.991576, 30.319135),
                        Point(59.991015, 30.321215),
                        Point(59.989240, 30.322062),
                        Point(59.985143, 30.319069)),
                    -i,
                    i
                ))
            }
        }*/
    }
}
