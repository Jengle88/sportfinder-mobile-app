package ru.riders.sportfinder.screen.setting_screens

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import ru.riders.sportfinder.screen.setting_screens.components.SettingCheckboxItem
import ru.riders.sportfinder.screen.setting_screens.components.SettingGroup
import ru.riders.sportfinder.screen.setting_screens.components.SettingLabelItem
import ru.riders.sportfinder.screen.setting_screens.components.SettingNavigationItem

@Composable
fun SettingScreen(
    navigateToAuth: () -> Unit,
    viewModel: SettingScreenViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val showToast = {
        Toast.makeText(context, "Still in progress", Toast.LENGTH_SHORT).show()
    }

    LaunchedEffect(key1 = true) {
        viewModel.logoutState.collect {
            if (it) {
                navigateToAuth()
            }
        }
    }

    Column {
        SettingGroup(
            groupTitle = "Common"
        ) {
            LazyColumn {
                item {
                    SettingNavigationItem(
                        title = "Edit profile",
                        navigateTo = {
                            showToast()
                        }
                    )
                }
                item {
                    SettingNavigationItem(
                        title = "Change password",
                        navigateTo = {
                            showToast()
                        }
                    )
                }
                item {
                    SettingCheckboxItem(
                        title = "Dark mode",
                        initialState = false,
                        updateCheckState = {}
                    )
                }
                item {
                    SettingCheckboxItem(
                        title = "Push notifications",
                        initialState = false,
                        updateCheckState = {}
                    )
                }
                item {
                    SettingLabelItem(
                        title = "Logout",
                        isDangerous = true,
                        action = {
                            viewModel.logoutUser()
                        }
                    )
                }
            }
        }
    }
}