package ru.riders.sportfinder.data.remote.dto

import androidx.compose.ui.graphics.painter.Painter
import com.google.android.gms.maps.model.LatLng
import ru.riders.sportfinder.domain.model.sport_court.SportCourt

data class SportCourtDto(
    val name: String,
    val courtId: Long,
    val coordinates: List<Double>,
    val tags: String? = null,
    val distance: Float? = null,
    val temperature: Float? = null,
    val resourceId: Painter? = null
)

fun SportCourtDto.toSportCourt() =
    SportCourt(
        name = name,
        courtId = courtId,
        coordinates = LatLng(coordinates[0], coordinates[1]),
        tags = tags,
        distance = distance,
        temperature = temperature,
        resourceId = resourceId
    )