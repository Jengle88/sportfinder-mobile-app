package ru.riders.sportfinder.data.repository

import ru.riders.sportfinder.data.remote.ServerApi
import ru.riders.sportfinder.data.remote.dto.RunningTrackDto
import ru.riders.sportfinder.data.remote.dto.RunningTracksDto
import ru.riders.sportfinder.domain.repository.RunningTracksRepository
import javax.inject.Inject

class RunningTracksRepositoryImpl @Inject constructor(
    private val api: ServerApi
) : RunningTracksRepository {
    override suspend fun getRunningTracks(): RunningTracksDto {
        // FIXME: Исправить на получение реальных данных 
        val result = listOf(
            RunningTrackDto("Мой первый маршрут", 1.5, 15, "Асфальт, река", emptyList(), 16, 0),
            RunningTrackDto("Побейте рекорд", 3.78, 16, "Бездорожье, деревья, площадка", emptyList(), 16, 1),
            RunningTrackDto("Крутой забег", 5.61, 16, "Асфальт", emptyList(), 17, 2),
            RunningTrackDto("Спортивная ходьба", 1.12, 15, "Асфальт", emptyList(), 16, 3),
            RunningTrackDto("Спортивная ходьба 2", 1.57, 15, "Асфальт", emptyList(), 16, 4),
            RunningTrackDto("Бег на природе", 3.24, 15, "Деревья, бездорожье", emptyList(), 16, 5),
        )
        return RunningTracksDto(result)//api.getRunningTracks()
    }

    override suspend fun getRunningTrack(runningTrackId: Int): RunningTrackDto {
        // TODO: Сделать вызов из api
        return RunningTrackDto("", 0.0, 0, "", emptyList(), 0, 0)
    }
}