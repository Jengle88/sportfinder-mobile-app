package ru.riders.sportfinder.screen.profile_screen.components

import androidx.compose.ui.graphics.painter.Painter

data class TabNavItem(
    val icon: Painter,
    val onTabClick: () -> Unit
)
