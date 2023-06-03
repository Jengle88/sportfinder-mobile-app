package ru.riders.sportfinder.domain.model.running_track

data class RunningTrackVOForCreate(
    val title: String,
    val distance: Double,
    val tags: List<Int>,
    val points: List<List<Double>>
)