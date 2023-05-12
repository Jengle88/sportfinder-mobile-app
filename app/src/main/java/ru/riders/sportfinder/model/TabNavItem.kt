package ru.riders.sportfinder.model

import androidx.compose.ui.graphics.painter.Painter

data class TabNavItem(
    val icon: Painter,
    val onTabClick: () -> Unit
)
