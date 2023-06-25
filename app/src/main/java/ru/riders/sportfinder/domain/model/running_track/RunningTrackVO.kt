package ru.riders.sportfinder.domain.model.running_track

import com.google.android.gms.maps.model.LatLng

data class RunningTrackVO(
    val title: String,
    val distance: Double,
    val tempOnStart: Int?,
    val tags: String,
    val points: List<LatLng>? = emptyList(),
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