package ru.riders.sportfinder.screen.commonComponents

import androidx.compose.ui.graphics.painter.Painter
import androidx.navigation.NavHostController

data class BottomNavItem(
    val icon: Painter,
    val onItemClick: (NavHostController) -> Unit
)
