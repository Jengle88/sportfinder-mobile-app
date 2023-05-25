package ru.riders.sportfinder.domain.use_case

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import ru.riders.sportfinder.common.ApiResultState
import ru.riders.sportfinder.data.db.UserProfileDao
import ru.riders.sportfinder.data.remote.dto.toSignIn
import ru.riders.sportfinder.domain.model.SignIn
import ru.riders.sportfinder.domain.model.toSignUp
import ru.riders.sportfinder.domain.repository.UserProfileRepository
import java.io.IOException
import javax.inject.Inject

class SignInUser @Inject constructor(
    private val userProfileDao: UserProfileDao,
    private val userProfileRepository: UserProfileRepository
) {
    operator fun invoke(login: String, password: String): Flow<ApiResultState<SignIn>> =
        flow {
            try {
                emit(ApiResultState.Loading())

                val signInData = userProfileRepository.signInUser(login, password).toSignIn()
                userProfileDao.insertUserProfile(signInData.toSignUp())
                emit(ApiResultState.Success(signInData))
            } catch (e: HttpException) {
                emit(ApiResultState.Error(e.localizedMessage ?: "An unexpected error occurred"))
            } catch (e: IOException) {
                emit(ApiResultState.Error("Could not reach server. Check your internet connection"))
            }
        }

}