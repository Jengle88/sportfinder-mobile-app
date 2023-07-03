package ru.riders.sportfinder.domain.use_case

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.riders.sportfinder.domain.repository.UserProfileRepository
import javax.inject.Inject

class LogoutUserProfile @Inject constructor(
    private val userProfileRepository: UserProfileRepository,
    private val updateToken: (String?) -> Unit
) {
    operator fun invoke(): Flow<Boolean> = flow {
        updateToken(null)
        emit(userProfileRepository.logoutUser())
    }
}
