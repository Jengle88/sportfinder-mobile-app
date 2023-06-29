package ru.riders.sportfinder.domain.repository

import ru.riders.sportfinder.data.remote.dto.CreateRunningTrackDto
import ru.riders.sportfinder.data.remote.dto.RunningTrackDto
import ru.riders.sportfinder.data.remote.dto.RunningTracksDto

interface RunningTracksRepository {

    suspend fun getRunningTracks(): RunningTracksDto

    suspend fun getRunningTrack(runningTrackId: Int): RunningTrackDto

    suspend fun createRunningTrack(
        name: String,
        distance: Double,
        points: Array<Array<Double>>
    ): CreateRunningTrackDto
}