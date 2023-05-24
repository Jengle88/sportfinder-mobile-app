package ru.riders.sportfinder.domain.repository

import ru.riders.sportfinder.data.remote.dto.SportCourtsDto

interface SportCourtsRepository {

    suspend fun getSportCourts(): SportCourtsDto
}