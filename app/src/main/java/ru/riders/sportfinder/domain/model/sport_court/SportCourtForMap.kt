package ru.riders.sportfinder.domain.model.sport_court

import com.yandex.mapkit.geometry.Point

data class SportCourtForMap(
    val courtId: Long,
    val coordinates: Point,
)
