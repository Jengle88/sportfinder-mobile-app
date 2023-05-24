package ru.riders.sportfinder.domain.repository

import ru.riders.sportfinder.data.RunningTracksDto

interface RunningTracksRepository {

    suspend fun getRunningTracks(): RunningTracksDto
}