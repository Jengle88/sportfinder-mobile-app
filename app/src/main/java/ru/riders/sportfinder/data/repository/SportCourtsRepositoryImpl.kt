package ru.riders.sportfinder.data.repository

import ru.riders.sportfinder.data.remote.ServerApi
import ru.riders.sportfinder.data.remote.dto.SportCourtsDto
import ru.riders.sportfinder.domain.repository.SportCourtsRepository
import javax.inject.Inject

class SportCourtsRepositoryImpl @Inject constructor(
    private val api: ServerApi
) : SportCourtsRepository {
    override suspend fun getSportCourts(): SportCourtsDto {
        return api.getSportCourts()
    }
}