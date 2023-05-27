package ru.riders.sportfinder.data.repository

import ru.riders.sportfinder.data.remote.ServerApi
import ru.riders.sportfinder.data.remote.dto.RunningTrackDto
import ru.riders.sportfinder.data.remote.dto.RunningTracksDto
import ru.riders.sportfinder.domain.repository.RunningTracksRepository

class RunningTracksRepositoryImpl(
    private val api: ServerApi
) : RunningTracksRepository {
    override suspend fun getRunningTracks(): RunningTracksDto {
        return api.getRunningTracks()
    }

    override suspend fun getRunningTrack(runningTrackId: Int): RunningTrackDto {
        // TODO: Сделать вызов из api
        return RunningTrackDto("", 0.0, 0, "", emptyList(), 0, 0)
    }
}