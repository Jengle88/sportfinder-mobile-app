package ru.riders.sportfinder.domain.use_case

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.riders.sportfinder.common.ApiResultState
import ru.riders.sportfinder.data.remote.dto.TagDto
import ru.riders.sportfinder.data.remote.dto.toTagVO
import ru.riders.sportfinder.domain.model.TagVO
import javax.inject.Inject

class GetTags @Inject constructor() {
    operator fun invoke(): Flow<ApiResultState<List<TagVO>>> = flow {
        emit(ApiResultState.Loading())

        // TODO: Заменить на получение тегов
        val tags = listOf(
            TagDto(0, "Асфальт"),
            TagDto(1, "Бездорожье"),
            TagDto(2, "Деревья"),
            TagDto(3, "Перекус"),
        )
        emit(ApiResultState.Success(tags.map { it.toTagVO() }))
    }
}