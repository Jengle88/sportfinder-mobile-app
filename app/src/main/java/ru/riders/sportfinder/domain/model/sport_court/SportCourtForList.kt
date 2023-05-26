package ru.riders.sportfinder.domain.model.sport_court

import androidx.compose.ui.graphics.painter.Painter

data class SportCourtForList(
    val courtId: Long,
    val name: String,
    val tags: String? = null,
    val distance: Float? = null,
    val temperature: Float? = null,
    val resourceId: Painter? = null,
)
