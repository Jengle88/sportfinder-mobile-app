package ru.riders.sportfinder.domain.model.sport_court

import androidx.compose.ui.graphics.painter.Painter
import com.yandex.mapkit.geometry.Point

data class SportCourt(
    val courtId: Long,
    val name: String,
    val coordinates: Point,
    val tags: String? = null,
    val distance: Float? = null,
    val temperature: Float? = null,
    val resourceId: Painter? = null,
)

fun SportCourt.toSportCourtForList() =
    SportCourtForList(
        courtId = courtId,
        name = name,
        tags = tags,
        distance = distance,
        temperature = temperature,
        resourceId = resourceId
    )

fun SportCourt.toSportCourtForMap() =
    SportCourtForMap(
        courtId = courtId,
        coordinates = coordinates
    )