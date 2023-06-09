package ru.riders.sportfinder.data.repository

import ru.riders.sportfinder.data.remote.ServerApi
import ru.riders.sportfinder.data.remote.dto.SportCourtsDto
import ru.riders.sportfinder.domain.repository.SportCourtsListRepository
import javax.inject.Inject

class SportCourtsListRepositoryImpl @Inject constructor(
    private val api: ServerApi
) : SportCourtsListRepository {
    override suspend fun getSportCourtsList(): SportCourtsDto {
        return api.getSportCourts()
    }
}