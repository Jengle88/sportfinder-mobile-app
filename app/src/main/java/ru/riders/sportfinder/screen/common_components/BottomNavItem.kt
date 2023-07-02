package ru.riders.sportfinder.screen.common_components

import androidx.compose.ui.graphics.painter.Painter

data class BottomNavItem(
    val icon: Painter,
    val onItemClick: () -> Unit
)
