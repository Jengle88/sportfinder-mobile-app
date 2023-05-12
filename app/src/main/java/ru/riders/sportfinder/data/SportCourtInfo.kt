package ru.riders.sportfinder.data

import androidx.compose.ui.graphics.painter.Painter

data class SportCourtInfo(
    val name: String,
    val tegs: List<String>? = null,
    val distance: Float? = null,
    val temperature: Float? = null,
    val resourceId: Painter? = null
)