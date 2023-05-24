package ru.riders.sportfinder.data.repository

import ru.riders.sportfinder.data.RunningTracksDto
import ru.riders.sportfinder.data.remote.ServerApi
import ru.riders.sportfinder.domain.repository.RunningTracksRepository

class RunningTracksRepositoryImpl(
    private val api: ServerApi
) : RunningTracksRepository {
    override suspend fun getRunningTracks(): RunningTracksDto {
        return api.getRunningTracks()
    }
}