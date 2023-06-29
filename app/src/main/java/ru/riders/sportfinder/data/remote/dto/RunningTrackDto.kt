package ru.riders.sportfinder.data.remote.dto

import android.util.Log
import com.google.android.gms.maps.model.LatLng
import com.google.gson.annotations.SerializedName
import ru.riders.sportfinder.domain.model.running_track.RunningTrackVO

data class RunningTrackDto(
    val id: Int,
    @SerializedName("label")
    val title: String,
    val points: Array<Array<Double>>?,
    val distance: Double,
    val creator: RunningTrackCreatorDto
)

fun RunningTrackDto.toRunningTrackVO(): RunningTrackVO {
    val points =
        if (this.points?.all { it.size == 2 } == true) this.points.map { LatLng(it[0], it[1]) }
        else {
            Log.d("RunningTrackDto", "Список содержит невалидные точки")
            emptyList()
        }
    // FIXME: Исправить на выставление получаемых параметров
    return RunningTrackVO(
        title = title,
        distance = distance,
        tempOnStart = 0,
        tags = "",
        points = points,
        tempOnEnd = 0,
        trackId = id,
    )
}