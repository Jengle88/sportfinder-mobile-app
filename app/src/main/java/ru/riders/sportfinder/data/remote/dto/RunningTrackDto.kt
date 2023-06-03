package ru.riders.sportfinder.data.remote.dto

import com.google.gson.annotations.SerializedName
import com.yandex.mapkit.geometry.Point
import ru.riders.sportfinder.domain.model.running_track.RunningTrackVO

data class RunningTrackDto(
    val title: String,
    @SerializedName("dist")
    val distance: Double,
    val tempOnStart: Int?,
    @SerializedName("tag")
    val tags: String,
    val points: List<Point>?,
    val tempOnEnd: Int?,
    @SerializedName("id")
    val trackId: Int
)

fun RunningTrackDto.toRunningTrack() =
    RunningTrackVO(
        title = title,
        distance = distance,
        tempOnStart = tempOnStart,
        tags = tags,
        points = points,
        tempOnEnd = tempOnEnd,
        trackId = trackId,
    )