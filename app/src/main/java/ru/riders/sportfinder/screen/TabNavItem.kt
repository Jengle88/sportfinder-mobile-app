package ru.riders.sportfinder.screen

import androidx.compose.ui.graphics.painter.Painter

data class TabNavItem(
    val icon: Painter,
    val onTabClick: () -> Unit
)
