package ru.riders.sportfinder.domain.use_case

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import ru.riders.sportfinder.common.ApiResultState
import ru.riders.sportfinder.data.remote.dto.toRunningTrackVO
import ru.riders.sportfinder.domain.model.running_track.RunningTrackVO
import ru.riders.sportfinder.domain.repository.RunningTracksRepository
import java.io.IOException
import javax.inject.Inject

class LoadRunningTracksList @Inject constructor(
    private val runningTracksRepository: RunningTracksRepository
) {

    operator fun invoke(): Flow<ApiResultState<List<RunningTrackVO>>> = flow {
        try {
            emit(ApiResultState.Loading())

            val sportCourts = runningTracksRepository.getRunningTracks()
            emit(ApiResultState.Success(sportCourts.runningTracks
                .map { it.toRunningTrackVO() }))
        } catch (e: HttpException) {
            emit(ApiResultState.Error(e.localizedMessage ?: "An unexpected error occurred"))
        } catch (e: IOException) {
            emit(ApiResultState.Error("Could not reach server. Check your internet connection"))
        }
    }
}