package ru.riders.sportfinder.model

import androidx.compose.ui.graphics.painter.Painter
import androidx.navigation.NavHostController

data class BottomNavItem(
    val icon: Painter,
    val onItemClick: (NavHostController) -> Unit
)
