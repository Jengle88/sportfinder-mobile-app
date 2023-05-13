package ru.riders.sportfinder.data


import com.yandex.mapkit.geometry.Point



data class TrackInfo(
    val title: String,
    val distance: Double,
    val tempOnStart: Int,
    val tags: List<String>,
    val points: List<Point>,
    val tempOnEnd:Int,
    val trackId:Int
)