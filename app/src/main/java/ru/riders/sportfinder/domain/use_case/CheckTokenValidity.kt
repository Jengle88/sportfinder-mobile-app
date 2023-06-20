package ru.riders.sportfinder.domain.use_case

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import ru.riders.sportfinder.common.ApiResultState
import ru.riders.sportfinder.domain.repository.UserProfileRepository
import java.io.IOException

class CheckTokenValidity(
    private val userProfileRepository: UserProfileRepository
) {

    operator fun invoke(): Flow<ApiResultState<Boolean>> = flow {
        try {
            emit(ApiResultState.Loading())

            val result = userProfileRepository.checkTokenValidity()
            emit(ApiResultState.Success(result))
        } catch (e: HttpException) {
            emit(ApiResultState.Error(e.localizedMessage ?: "An unexpected error occurred"))
        } catch (e: IOException) {
            emit(ApiResultState.Error("Could not reach server. Check your internet connection"))
        }
    }
}