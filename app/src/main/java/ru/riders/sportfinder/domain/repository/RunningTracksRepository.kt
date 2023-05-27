package ru.riders.sportfinder.domain.repository

import ru.riders.sportfinder.data.remote.dto.RunningTrackDto
import ru.riders.sportfinder.data.remote.dto.RunningTracksDto

interface RunningTracksRepository {

    suspend fun getRunningTracks(): RunningTracksDto

    suspend fun getRunningTrack(runningTrackId: Int): RunningTrackDto
}