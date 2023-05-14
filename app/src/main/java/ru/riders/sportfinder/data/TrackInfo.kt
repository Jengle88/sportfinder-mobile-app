package ru.riders.sportfinder.data


import com.google.gson.annotations.SerializedName
import com.yandex.mapkit.geometry.Point

data class TrackInfoListResponse(
    @SerializedName("routes")
    var runningTracks: List<TrackInfoResponse> = emptyList()
)

data class TrackInfoResponse(
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