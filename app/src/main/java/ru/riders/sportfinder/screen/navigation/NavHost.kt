package ru.riders.sportfinder.screen.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import ru.riders.sportfinder.screen.authorization_screen.AuthorizationScreen
import ru.riders.sportfinder.screen.common_components.JCMapView
import ru.riders.sportfinder.screen.create_running_track_screen.CreateRunningTrackScreen
import ru.riders.sportfinder.screen.profile_screen.ProfileScreen
import ru.riders.sportfinder.screen.registration_screen.RegistrationScreen
import ru.riders.sportfinder.screen.running_tracks_list_screen.RunningTracksListScreen
import ru.riders.sportfinder.screen.sport_courts_list_screen.SportCourtsListScreen
import ru.riders.sportfinder.screen.sport_courts_map_screen.SportCourtMapScreen
import ru.riders.sportfinder.screen.watch_running_track_screen.WatchRunningTrackScreen


@Composable
fun MainScreenNavHost(
    navHostController: NavHostController,
    isSupportedBottomNav: MutableState<Boolean>
) {
    NavHost(navController = navHostController, startDestination = Screens.AUTH_SCREEN.route) {
        composable(route = Screens.AUTH_SCREEN.route) {
            isSupportedBottomNav.value = false
            AuthorizationScreen(
                navigateToProfileScreen = { navHostController.navigate(Screens.PROFILE_SCREEN.route) },
                navigateToRegistrationScreen = { navHostController.navigate(Screens.REG_SCREEN.route) },
            )
        }
        composable(route = Screens.REG_SCREEN.route) {
            isSupportedBottomNav.value = false
            RegistrationScreen(
                navigateToProfileScreen = { navHostController.navigate(Screens.PROFILE_SCREEN.route) },
                navigateToAuthorizationScreen = { navHostController.navigate(Screens.AUTH_SCREEN.route) }
            )
        }
        composable(route = Screens.PROFILE_SCREEN.route) {
            isSupportedBottomNav.value = true
            ProfileScreen()
        }
        composable(route = Screens.SPORT_COURT_MAP_SCREEN.route) {
            isSupportedBottomNav.value = true
            val jcMapView = JCMapView(LocalContext.current)

            SportCourtMapScreen(
                jcMapView,
                navigateToSportCourtListScreen = { navHostController.navigate(Screens.SPORT_COURT_LIST_SCREEN.route) }
            )
        }
        composable(route = Screens.SPORT_COURT_LIST_SCREEN.route) {
            isSupportedBottomNav.value = true
            SportCourtsListScreen(
                navigateToSportCourtMapScreen = { navHostController.navigate(Screens.SPORT_COURT_MAP_SCREEN.route) }
            )
        }
        composable(route = Screens.RUNNING_TRACKS_LIST_SCREEN.route) {
            isSupportedBottomNav.value = true
            RunningTracksListScreen(
                navigateToWatchRunningTrackScreen = { trackId -> navHostController.navigate(Screens.WATCH_RUNNING_TRACK_SCREEN.route + "/$trackId") },
                navigateToCreateTrackScreen = { navHostController.navigate(Screens.CREATE_TRACK_SCREEN.route) }
            )
        }
        composable(route = Screens.WATCH_RUNNING_TRACK_SCREEN.route + "/{trackInfoNumber}",
            arguments = listOf(navArgument("trackInfoNumber") {
                type = NavType.IntType
            })) {
            val jcMapView = JCMapView(LocalContext.current)

            isSupportedBottomNav.value = true
            WatchRunningTrackScreen(jcMapView)
        }
        composable(route = Screens.CREATE_TRACK_SCREEN.route) {
            isSupportedBottomNav.value = true

            val jcMapView = JCMapView(LocalContext.current)

            CreateRunningTrackScreen(jcMapView)
        }
    }
}
