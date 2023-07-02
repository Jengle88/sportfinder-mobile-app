package ru.riders.sportfinder.screen.navigation

import androidx.navigation.NavHostController

class NavigationController(
    _navHostController: NavHostController
) {
    var navHostController: NavHostController = _navHostController
        private set

    fun navigateBack() =
        navHostController.popBackStack()
    
    fun navigateToProfile() =
        navHostController.navigate(ScreensSubgraphs.PROFILE.route)

    fun navigateToSportCourt() =
        navHostController.navigate(ScreensSubgraphs.SPORT_COURT.route)

    fun navigateToRunningTrack() =
        navHostController.navigate(ScreensSubgraphs.RUNNING_TRACK.route)

    fun navigateFromAuthToProfile() =
        navHostController.navigate(ScreensSubgraphs.PROFILE.route) {
            popUpTo(ScreensSubgraphs.AUTH.route) {
                inclusive = true
            }
        }

    fun navigateToRegistrationScreen() =
        navHostController.navigate(Screens.REG_SCREEN.route)
    
    fun navigateToAuthScreen() =
        navHostController.navigate(Screens.AUTH_SCREEN.route)
    
    fun navigateToSettings() =
        navHostController.navigate(ScreensSubgraphs.SETTINGS.route)
    
    fun navigateToSportCourtListScreen() =
        navHostController.navigate(Screens.SPORT_COURT_LIST_SCREEN.route)
    
    fun navigateToSportCourtMapScreen() =
        navHostController.navigate(Screens.SPORT_COURT_MAP_SCREEN.route)
    
    fun navigateToRunningTrackScreen(trackId: Int) =
        navHostController.navigate(Screens.RUNNING_TRACK_MAP_SCREEN.route + "/$trackId")
    
    fun navigateToCreateTrackScreen () =
        navHostController.navigate(Screens.CREATE_TRACK_SCREEN.route)

    fun navigateFromSettingsToAuth() =
        navHostController.navigate(ScreensSubgraphs.AUTH.route) {
            popUpTo(ScreensSubgraphs.SETTINGS.route) {
                inclusive = true
            }
        }
}