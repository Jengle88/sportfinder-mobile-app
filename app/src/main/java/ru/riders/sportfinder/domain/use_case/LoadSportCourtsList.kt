package ru.riders.sportfinder.domain.use_case

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import ru.riders.sportfinder.common.ApiResultState
import ru.riders.sportfinder.data.remote.dto.toSportCourtListItem
import ru.riders.sportfinder.domain.model.SportCourtListItem
import ru.riders.sportfinder.domain.repository.SportCourtsListRepository
import java.io.IOException
import javax.inject.Inject

class LoadSportCourtsList @Inject constructor(
    private val sportCourtsListRepository: SportCourtsListRepository
) {
    operator fun invoke(): Flow<ApiResultState<List<SportCourtListItem>>> = flow {
        try {
            emit(ApiResultState.Loading())

            val sportCourts = sportCourtsListRepository.getSportCourtsList()
            emit(ApiResultState.Success(sportCourts.sportCourts.map { it.toSportCourtListItem() }))
        } catch (e: HttpException) {
            emit(ApiResultState.Error(e.localizedMessage ?: "An unexpected error occurred"))
        } catch (e: IOException) {
            emit(ApiResultState.Error("Could not reach server. Check your internet connection"))
        }
    }

}