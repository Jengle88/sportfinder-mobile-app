package ru.riders.sportfinder.data.remote.dto

import androidx.compose.ui.graphics.painter.Painter
import ru.riders.sportfinder.domain.model.SportCourtListItem

data class SportCourtDto(
    val name: String,
    val courtId: Long,
    val coordinates: List<Double>,
    val tags: String? = null,
    val distance: Float? = null,
    val temperature: Float? = null,
    val resourceId: Painter? = null
)

fun SportCourtDto.toSportCourtListItem() =
    SportCourtListItem(
        name = name,
        courtId = courtId,
        tags = tags,
        distance = distance,
        temperature = temperature,
    )