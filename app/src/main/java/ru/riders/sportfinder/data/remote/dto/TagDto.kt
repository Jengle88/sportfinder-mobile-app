package ru.riders.sportfinder.data.remote.dto

import ru.riders.sportfinder.domain.model.TagVO

data class TagDto(
    val id: Int, val tag: String
)

fun TagDto.toTagVO() = TagVO(
    id = id,
    tag = tag
)
