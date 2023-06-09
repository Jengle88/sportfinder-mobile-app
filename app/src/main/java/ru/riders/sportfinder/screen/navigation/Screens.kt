package ru.riders.sportfinder.screen.navigation

enum class Screens(val route: String) {
    AUTH_SCREEN("auth_screen"),
    REG_SCREEN("reg_screen"),
    PROFILE_SCREEN("profile_screen"),
    SPORT_COURT_MAP_SCREEN("sport_court_map_screen"),
    SPORT_COURT_LIST_SCREEN("sport_court_list_screen"),
    RUNNING_TRACKS_LIST_SCREEN("running_tracks_list_screen"),
    RUNNING_TRACK_MAP_SCREEN("running_track_map_screen"),
    CREATE_TRACK_SCREEN("create_track_screen"),
    MAIN_SETTINGS_SCREEN("main_settings_screen")
}

enum class ScreensSubgraphs(val route: String) {
    AUTH("auth"),
    PROFILE("profile"),
    SPORT_COURT("sport_court"),
    RUNNING_TRACK("running_track"),
    SETTINGS("settings")
}