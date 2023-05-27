package ru.riders.sportfinder.domain.use_case

import com.yandex.mapkit.geometry.Point
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import ru.riders.sportfinder.common.ApiResultState
import ru.riders.sportfinder.data.remote.dto.RunningTrackDto
import ru.riders.sportfinder.data.remote.dto.toRunningTrack
import ru.riders.sportfinder.domain.model.running_track.RunningTrack
import ru.riders.sportfinder.domain.repository.RunningTracksRepository
import java.io.IOException
import javax.inject.Inject

class LoadRunningTrack @Inject constructor(
    private val runningTracksRepository: RunningTracksRepository
) {

    operator fun invoke(runningTrackId: Int): Flow<ApiResultState<RunningTrack>> = flow {
        try {
            emit(ApiResultState.Loading())

            // FIXME: Добавить загрузку из сети
//            val runningTrack = runningTracksRepository.getRunningTrack()

            // ------------
            val points = mutableListOf<Point>()
            val start = Point(59.935227, 30.329152)
            points.add(start)
            points.add(Point(start.latitude, start.longitude + 0.013))
            points.add(Point(start.latitude + 0.063, start.longitude + 0.037))
            points.add(Point(start.latitude + 0.083, start.longitude))
            points.add(Point(start.latitude + 0.039, start.longitude))

            val runningTrack = RunningTrackDto(
                "Беговой маршрут 1",
                1.5,
                12,
                "Набережная",
                points,
                15,
                runningTrackId
            )
            // ------------

            emit(ApiResultState.Success(runningTrack.toRunningTrack()))
        } catch (e: HttpException) {
            emit(ApiResultState.Error(e.localizedMessage ?: "An unexpected error occurred"))
        } catch (e: IOException) {
            emit(ApiResultState.Error("Could not reach server. Check your internet connection"))
        }
    }
}