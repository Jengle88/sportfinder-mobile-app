package ru.riders.sportfinder.data.remote.dto


import com.google.gson.annotations.SerializedName

data class RunningTracksDto(
    @SerializedName("routes")
    var runningTracks: List<RunningTrackDto> = emptyList()
)

