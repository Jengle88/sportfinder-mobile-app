package ru.riders.sportfinder.domain.use_case

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.riders.sportfinder.common.ApiResultState
import javax.inject.Inject

class CreateRunningTrack @Inject constructor() {

    operator fun invoke(): Flow<ApiResultState<Boolean>> = flow {

    }

}