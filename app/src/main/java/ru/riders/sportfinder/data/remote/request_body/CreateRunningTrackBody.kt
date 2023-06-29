package ru.riders.sportfinder.data.remote.request_body

data class CreateRunningTrackBody(
    val name: String,
    val distance: Double,
    val points: Array<Array<Double>>
)
