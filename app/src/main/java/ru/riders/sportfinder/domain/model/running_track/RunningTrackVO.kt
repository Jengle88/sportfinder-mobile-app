package ru.riders.sportfinder.domain.model.running_track

import com.yandex.mapkit.geometry.Point

data class RunningTrackVO(
    val title: String,
    val distance: Double,
    val tempOnStart: Int?,
    val tags: String,
    val points: List<Point>?,
    val tempOnEnd: Int?,
    val trackId: Int
)

fun RunningTrackVO.toRunningTrackForList() =
    RunningTrackVOForList(
        trackId = trackId,
        title = title,
        distance = distance,
        tags = tags,
        tempOnStart = tempOnStart
    )

fun RunningTrackVO.toRunningTrackForMap() =
    RunningTrackVOForMap(
        trackId = trackId,
        distance = distance,
        points = points,
        tempOnStart = tempOnStart,
        tempOnEnd = tempOnEnd
    )