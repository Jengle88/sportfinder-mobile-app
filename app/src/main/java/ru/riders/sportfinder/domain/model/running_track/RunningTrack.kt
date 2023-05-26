package ru.riders.sportfinder.domain.model.running_track

import com.yandex.mapkit.geometry.Point

data class RunningTrack(
    val title: String,
    val distance: Double,
    val tempOnStart: Int?,
    val tags: String,
    val points: List<Point>?,
    val tempOnEnd: Int?,
    val trackId: Int
)

fun RunningTrack.toRunningTrackForList() =
    RunningTrackForList(
        trackId = trackId,
        title = title,
        distance = distance,
        tags = tags,
        tempOnStart = tempOnStart
    )

fun RunningTrack.toRunningTrackForMap() =
    RunningTrackForMap(
        trackId = trackId,
        distance = distance,
        points = points,
        tempOnStart = tempOnStart,
        tempOnEnd = tempOnEnd
    )