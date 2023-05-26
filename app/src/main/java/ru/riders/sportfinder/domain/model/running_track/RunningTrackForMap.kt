package ru.riders.sportfinder.domain.model.running_track

import com.yandex.mapkit.geometry.Point

data class RunningTrackForMap(
    val trackId: Int,
    val distance: Double,
    val points: List<Point>?,
    val tempOnStart: Int?,
    val tempOnEnd: Int?,
)
