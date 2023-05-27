package ru.riders.sportfinder.domain.use_case

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import ru.riders.sportfinder.common.ApiResultState
import ru.riders.sportfinder.data.db.UserProfileDao
import ru.riders.sportfinder.data.remote.dto.toSignUp
import ru.riders.sportfinder.domain.model.SignUp
import ru.riders.sportfinder.domain.repository.UserProfileRepository
import java.io.IOException
import javax.inject.Inject

class SignUpUser @Inject constructor(
    private val userProfileDao: UserProfileDao,
    private val userProfileRepository: UserProfileRepository
) {
    operator fun invoke(login: String, password: String): Flow<ApiResultState<SignUp>> =
        flow {
            try {
                emit(ApiResultState.Loading())

                val signUpData = userProfileRepository.signUpUser(login, password).toSignUp()
                userProfileDao.insertUserProfile(signUpData)
                emit(ApiResultState.Success(signUpData))
            } catch (e: HttpException) {
                emit(ApiResultState.Error(e.localizedMessage ?: "An unexpected error occurred"))
            } catch (e: IOException) {
                emit(ApiResultState.Error("Could not reach server. Check your internet connection"))
            }
        }
}