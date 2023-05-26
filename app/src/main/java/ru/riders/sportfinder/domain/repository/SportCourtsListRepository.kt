package ru.riders.sportfinder.domain.repository

import ru.riders.sportfinder.data.remote.dto.SportCourtsDto

interface SportCourtsListRepository {
    suspend fun getSportCourtsList(): SportCourtsDto
}
