package ru.riders.sportfinder.data.repository

import ru.riders.sportfinder.common.utils.NetworkUtils
import ru.riders.sportfinder.data.db.UserProfileDao
import ru.riders.sportfinder.data.remote.ServerApi
import ru.riders.sportfinder.data.remote.dto.CreateRunningTrackDto
import ru.riders.sportfinder.data.remote.dto.RunningTrackDto
import ru.riders.sportfinder.data.remote.dto.RunningTracksDto
import ru.riders.sportfinder.data.remote.request_body.CreateRunningTrackBody
import ru.riders.sportfinder.domain.repository.RunningTracksRepository
import javax.inject.Inject

class RunningTracksRepositoryImpl @Inject constructor(
    private val userProfileDao: UserProfileDao,
    private val api: ServerApi
) : RunningTracksRepository {
    override suspend fun getRunningTracks(): RunningTracksDto {
        return api.getRunningTracks()
    }

    override suspend fun getRunningTrack(runningTrackId: Int): RunningTrackDto {
        return api.getRunningTrack(runningTrackId)
    }

    override suspend fun createRunningTrack(
        name: String,
        distance: Double,
        points: Array<Array<Double>>
    ): CreateRunningTrackDto {
        return api.createRunningTrack(
            NetworkUtils.asBearerHeader(userProfileDao.getUserToken().token),
            CreateRunningTrackBody(name, distance, points)
        )
    }
}