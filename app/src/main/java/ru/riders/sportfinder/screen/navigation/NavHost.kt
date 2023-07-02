package ru.riders.sportfinder.screen.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import ru.riders.sportfinder.MainActivityViewModel
import ru.riders.sportfinder.screen.auth_screens.authorization_screen.AuthorizationScreen
import ru.riders.sportfinder.screen.auth_screens.registration_screen.RegistrationScreen
import ru.riders.sportfinder.screen.common_components.TopBarType
import ru.riders.sportfinder.screen.profile_screens.profile_screen.ProfileScreen
import ru.riders.sportfinder.screen.running_track_screens.create_running_track_screen.CreateRunningTrackScreen
import ru.riders.sportfinder.screen.running_track_screens.running_track_map_screen.RunningTrackMapScreen
import ru.riders.sportfinder.screen.running_track_screens.running_tracks_list_screen.RunningTracksListScreen
import ru.riders.sportfinder.screen.setting_screens.SettingScreen
import ru.riders.sportfinder.screen.sport_court_screens.sport_courts_list_screen.SportCourtsListScreen
import ru.riders.sportfinder.screen.sport_court_screens.sport_courts_map_screen.SportCourtMapScreen


@Composable
fun MainScreenNavHost(
    navHostController: NavHostController,
    isSupportedBottomNav: MutableState<Boolean>,
    topBarType: MutableState<TopBarType?>,
    viewModel: MainActivityViewModel
) {
    NavHost(navController = navHostController, startDestination = ScreensSubgraphs.AUTH.route) {
        navigation(
            startDestination = Screens.AUTH_SCREEN.route,
            route = ScreensSubgraphs.AUTH.route
        ) {
            composable(
                route = Screens.AUTH_SCREEN.route
            ) {
                isSupportedBottomNav.value = false
                topBarType.value = null
                AuthorizationScreen(
                    viewModel.isForceAuth,
                    navigateToProfileScreen = {
                        navHostController.navigate(Screens.PROFILE_SCREEN.route) {
                            popUpTo(ScreensSubgraphs.AUTH.route) {
                                inclusive = true
                            }
                        }
                    },
                    navigateToRegistrationScreen = { navHostController.navigate(Screens.REG_SCREEN.route) },
                )
            }
            composable(route = Screens.REG_SCREEN.route) {
                isSupportedBottomNav.value = false
                topBarType.value = null
                RegistrationScreen(
                    navigateToProfileScreen = { navHostController.navigate(Screens.PROFILE_SCREEN.route) {
                        popUpTo(ScreensSubgraphs.AUTH.route) {
                            inclusive = true
                        }
                    } },
                    navigateToAuthorizationScreen = { navHostController.navigate(Screens.AUTH_SCREEN.route) }
                )
            }
        }

        navigation(startDestination = Screens.PROFILE_SCREEN.route, route = ScreensSubgraphs.PROFILE.route) {
            composable(route = Screens.PROFILE_SCREEN.route) {
                isSupportedBottomNav.value = true
                topBarType.value = TopBarType.ProfileTopBar(navigateToSettings =  {
                    navHostController.navigate(ScreensSubgraphs.SETTINGS.route)
                })
                ProfileScreen()
            }
        }

        navigation(startDestination = Screens.SPORT_COURT_MAP_SCREEN.route, route = ScreensSubgraphs.SPORT_COURT.route) {
            composable(route = Screens.SPORT_COURT_MAP_SCREEN.route) {
                isSupportedBottomNav.value = true
                topBarType.value = null
                SportCourtMapScreen(
                    navigateToSportCourtListScreen = { navHostController.navigate(Screens.SPORT_COURT_LIST_SCREEN.route) }
                )
            }
            composable(route = Screens.SPORT_COURT_LIST_SCREEN.route) {
                isSupportedBottomNav.value = true
                topBarType.value = null
                SportCourtsListScreen(
                    navigateToSportCourtMapScreen = { navHostController.navigate(Screens.SPORT_COURT_MAP_SCREEN.route) }
                )
            }
        }

        navigation(startDestination = Screens.RUNNING_TRACKS_LIST_SCREEN.route, route = ScreensSubgraphs.RUNNING_TRACK.route) {
            composable(route = Screens.RUNNING_TRACKS_LIST_SCREEN.route) {
                isSupportedBottomNav.value = true
                topBarType.value = null
                RunningTracksListScreen(
                    navigateToWatchRunningTrackScreen = { trackId -> navHostController.navigate(Screens.RUNNING_TRACK_MAP_SCREEN.route + "/$trackId") },
                    navigateToCreateTrackScreen = { navHostController.navigate(Screens.CREATE_TRACK_SCREEN.route) }
                )
            }
            composable(route = Screens.RUNNING_TRACK_MAP_SCREEN.route + "/{trackInfoNumber}",
                arguments = listOf(navArgument("trackInfoNumber") {
                    type = NavType.IntType
                })
            ) {
                isSupportedBottomNav.value = true
                topBarType.value = null
                RunningTrackMapScreen()
            }
            composable(route = Screens.CREATE_TRACK_SCREEN.route) {
                isSupportedBottomNav.value = true
                topBarType.value = null
                CreateRunningTrackScreen(
                    navigateToRunningTrackScreen = { trackId -> navHostController.navigate(Screens.RUNNING_TRACK_MAP_SCREEN.route + "/$trackId") }
                )
            }
        }

        navigation(startDestination = Screens.MAIN_SETTINGS_SCREEN.route, route = ScreensSubgraphs.SETTINGS.route) {
            composable(route = Screens.MAIN_SETTINGS_SCREEN.route) {
                isSupportedBottomNav.value = true
                topBarType.value = TopBarType.SettingsTopBar(navigateBack = {
                    navHostController.popBackStack()
                })
                SettingScreen(
                    navigateToAuth = {
                        navHostController.navigate(ScreensSubgraphs.AUTH.route) {
                            popUpTo(ScreensSubgraphs.PROFILE.route) {
                                inclusive = true
                            }
                            viewModel.enableForceAuth()
                        }
                    }
                )
            }
        }
    }
}
