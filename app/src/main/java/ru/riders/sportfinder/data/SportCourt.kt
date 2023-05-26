package ru.riders.sportfinder.data

import androidx.compose.ui.graphics.painter.Painter
import com.yandex.mapkit.geometry.Point

data class SportCourt(
    val name: String,
    val courtId: Long,
    val coordinates: Point,
    val tags: String? = null,
    val distance: Float? = null,
    val temperature: Float? = null,
    val resourceId: Painter? = null
)