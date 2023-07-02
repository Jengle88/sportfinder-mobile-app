package ru.riders.sportfinder.screen.common_components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.KeyboardArrowLeft
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import ru.riders.sportfinder.screen.ui.theme.SportFinderLightColorScheme

sealed class TopBarType(val topBar: @Composable () -> Unit) {
    @OptIn(ExperimentalMaterial3Api::class)
    class ProfileTopBar(var navigateToSettings: () -> Unit) : TopBarType(topBar = {
        TopAppBar(
            title = {
                Text(
                    modifier = Modifier
                        .fillMaxWidth(),
                    text = "Sport Finder",
                    color = SportFinderLightColorScheme.onPrimary,
                    fontWeight = FontWeight.Bold
                )
            },
            colors = TopAppBarDefaults.smallTopAppBarColors(
                containerColor = SportFinderLightColorScheme.primary,
                actionIconContentColor = SportFinderLightColorScheme.onPrimary
            ),
            actions = {
                IconButton(onClick = { navigateToSettings() }) {
                    Icon(Icons.Outlined.Settings, null)
                }
            }
        )
    })

    @OptIn(ExperimentalMaterial3Api::class)
    class SettingsTopBar(var navigateBack: () -> Unit) : TopBarType(topBar = {
        TopAppBar(
            title = { Text("") },
            colors = TopAppBarDefaults.smallTopAppBarColors(
                containerColor = SportFinderLightColorScheme.primary,
                navigationIconContentColor = SportFinderLightColorScheme.onPrimary
            ),
            navigationIcon = {
                IconButton(onClick = { navigateBack() }) {
                    Icon(Icons.Outlined.KeyboardArrowLeft, null)
                }
            }
        )
    })
}
