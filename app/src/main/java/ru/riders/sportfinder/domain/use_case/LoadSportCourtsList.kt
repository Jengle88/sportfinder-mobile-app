package ru.riders.sportfinder.domain.use_case

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import ru.riders.sportfinder.common.ApiResultState
import ru.riders.sportfinder.data.remote.dto.toSportCourt
import ru.riders.sportfinder.domain.model.sport_court.SportCourt
import ru.riders.sportfinder.domain.repository.SportCourtsListRepository
import java.io.IOException
import javax.inject.Inject

class LoadSportCourtsList @Inject constructor(
    private val sportCourtsListRepository: SportCourtsListRepository
) {
    operator fun invoke(): Flow<ApiResultState<List<SportCourt>>> = flow {
        try {
            emit(ApiResultState.Loading())

            val sportCourts = sportCourtsListRepository.getSportCourtsList()
            emit(ApiResultState.Success(sportCourts.sportCourts
                .filter { it.coordinates.size == 2 }
                .map { it.toSportCourt() }))
        } catch (e: HttpException) {
            emit(ApiResultState.Error(e.localizedMessage ?: "An unexpected error occurred"))
        } catch (e: IOException) {
            emit(ApiResultState.Error("Could not reach server. Check your internet connection"))
        }
    }

}