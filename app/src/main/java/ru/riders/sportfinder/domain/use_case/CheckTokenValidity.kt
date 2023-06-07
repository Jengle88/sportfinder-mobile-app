package ru.riders.sportfinder.domain.use_case

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import ru.riders.sportfinder.common.ApiResultState
import ru.riders.sportfinder.data.db.UserProfileDao
import ru.riders.sportfinder.data.remote.ServerApi
import java.io.IOException

class CheckTokenValidity(
    private val serverApi: ServerApi,
    private val userProfileDao: UserProfileDao
) {

    operator fun invoke(): Flow<ApiResultState<Boolean>> = flow {
        try {
            emit(ApiResultState.Loading())

            val currToken = userProfileDao.getUserToken()?.token ?: "" // null может возникать, если таблица пустая
            val serverToken = serverApi.refreshToken(currToken).token
            emit(ApiResultState.Success(currToken == serverToken))
        } catch (e: HttpException) {
            emit(ApiResultState.Error(e.localizedMessage ?: "An unexpected error occurred"))
        } catch (e: IOException) {
            emit(ApiResultState.Error("Could not reach server. Check your internet connection"))
        }
    }
}