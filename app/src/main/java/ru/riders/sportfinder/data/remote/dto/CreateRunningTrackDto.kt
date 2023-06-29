package ru.riders.sportfinder.data.remote.dto

import com.google.gson.annotations.SerializedName

data class CreateRunningTrackDto(
    @SerializedName("route_id")
    val routeId: Int
)
