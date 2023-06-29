package ru.riders.sportfinder.domain.use_case

import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import ru.riders.sportfinder.common.ApiResultState
import ru.riders.sportfinder.domain.repository.RunningTracksRepository
import java.io.IOException
import javax.inject.Inject

class CreateRunningTrack @Inject constructor(
    private val runningTracksRepository: RunningTracksRepository
) {

    operator fun invoke(
        name: String,
        distance: Double,
        points: List<LatLng>
    ): Flow<ApiResultState<Int>> = flow {
        try {
            emit(ApiResultState.Loading())

            val routeId = runningTracksRepository.createRunningTrack(
                name,
                distance,
                Array(points.size) { arrayOf(points[it].latitude, points[it].longitude) }
            ).routeId
            emit(ApiResultState.Success(routeId))
        } catch (e: HttpException) {
            emit(ApiResultState.Error(e.localizedMessage ?: "An unexpected error occurred"))
        } catch (e: IOException) {
            emit(ApiResultState.Error("Could not reach server. Check your internet connection"))
        }
    }
}