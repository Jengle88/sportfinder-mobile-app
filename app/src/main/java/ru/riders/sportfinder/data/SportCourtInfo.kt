package ru.riders.sportfinder.data

import androidx.compose.ui.graphics.painter.Painter
import com.yandex.mapkit.geometry.Point

data class SportCourtInfo(
    val name: String,
    val courtId: Int,
    val coordinates: Point,
    val tags: List<String>? = null,
    val distance: Float? = null,
    val temperature: Float? = null,
    val resourceId: Painter? = null
)