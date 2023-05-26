package ru.riders.sportfinder.domain.model.running_track

data class RunningTrackForList(
    val trackId: Int,
    val title: String,
    val distance: Double,
    val tags: String,
    val tempOnStart: Int?
)
