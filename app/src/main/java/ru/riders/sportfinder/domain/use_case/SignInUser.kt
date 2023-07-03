package ru.riders.sportfinder.domain.use_case

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import ru.riders.sportfinder.common.ApiResultState
import ru.riders.sportfinder.domain.repository.UserProfileRepository
import java.io.IOException
import javax.inject.Inject

class SignInUser @Inject constructor(
    private val userProfileRepository: UserProfileRepository,
    private val updateToken: (String?) -> Unit
) {
    operator fun invoke(login: String, password: String): Flow<ApiResultState<Boolean>> =
        flow {
            try {
                emit(ApiResultState.Loading())

                val signInData = userProfileRepository.signInUser(login, password)
                if (signInData.token.isNotEmpty()) {
                    updateToken(signInData.token)
                    emit(ApiResultState.Success(true))
                } else
                    emit(ApiResultState.Success(false))
            } catch (e: HttpException) {
                emit(ApiResultState.Error(e.localizedMessage ?: "An unexpected error occurred"))
            } catch (e: IOException) {
                emit(ApiResultState.Error("Could not reach server. Check your internet connection"))
            }
        }

}