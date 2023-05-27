package ru.riders.sportfinder

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.ramcosta.composedestinations.rememberNavHostEngine
import com.yandex.mapkit.MapKitFactory
import dagger.hilt.android.AndroidEntryPoint
import ru.riders.sportfinder.screen.CreateTrackScreen
import ru.riders.sportfinder.screen.Screens
import ru.riders.sportfinder.screen.authorization_screen.AuthorizationScreen
import ru.riders.sportfinder.screen.common_components.BottomNavItem
import ru.riders.sportfinder.screen.common_components.JCMapView
import ru.riders.sportfinder.screen.profile_screen.ProfileScreen
import ru.riders.sportfinder.screen.registration_screen.RegistrationScreen
import ru.riders.sportfinder.screen.running_tracks_list_screen.RunningTracksListScreen
import ru.riders.sportfinder.screen.sport_courts_list_screen.SportCourtsListScreen
import ru.riders.sportfinder.screen.sport_courts_map_screen.SportCourtMapScreen
import ru.riders.sportfinder.screen.ui.theme.SportFinderLightColorScheme
import ru.riders.sportfinder.screen.ui.theme.SportFinderTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    lateinit var jcMapView: JCMapView

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MapKitFactory.initialize(this)

        jcMapView = JCMapView(this)

        setContent {
            val isSupportedBottomNav = remember { mutableStateOf(false) }

            val engine = rememberNavHostEngine()
            val navHostController = engine.rememberNavController()

            val bottomItems = listOf(
                BottomNavItem(
                    painterResource(R.drawable.ic_account_white_24)
                ) { it.navigate(Screens.PROFILE_SCREEN.route) },
                BottomNavItem(
                    painterResource(R.drawable.ic_location_white_24)
                ) { it.navigate(Screens.SPORT_COURT_MAP_SCREEN.route) },
                BottomNavItem(
                    painterResource(R.drawable.ic_runner_white_24)
                ) { it.navigate(Screens.TRACK_LIST_SCREEN.route) },
            )

            SportFinderTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Scaffold(
                        modifier = Modifier.fillMaxSize(),
                        bottomBar = {
                            if (isSupportedBottomNav.value) {
                                BottomBar(bottomItems, navHostController)
                            }
                        }
                    ) { paddingValues ->
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(paddingValues)
                        ) {
                            MainScreenNavHost(
                                jcMapView = jcMapView,
                                navHostController = navHostController,
                                isSupportedBottomNav = isSupportedBottomNav
                            )
                        }
                    }
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        MapKitFactory.getInstance().onStart()
        jcMapView.onStart()
    }

    override fun onStop() {
        MapKitFactory.getInstance().onStop()
        jcMapView.onStop()
        super.onStop()
    }
}


@Composable
private fun BottomBar(
    items: List<BottomNavItem>,
    navHostController: NavHostController,
) {
    var selectedId by remember { mutableStateOf(0) }
    BottomNavigation(
        backgroundColor = SportFinderLightColorScheme.primary
    ) {
        items.forEachIndexed { index, bottomNavItem ->
            BottomNavigationItem(
                selected = selectedId == index,
                onClick = {
                    selectedId = index
                    bottomNavItem.onItemClick(navHostController)
                },
                selectedContentColor = SportFinderLightColorScheme.onPrimary,
                unselectedContentColor = SportFinderLightColorScheme.onPrimary,
                icon = {
                    Icon(
                        painter = bottomNavItem.icon,
                        contentDescription = null
                    )
                }
            )
        }
    }
}

@Composable
fun MainScreenNavHost(
    jcMapView: JCMapView,
    navHostController: NavHostController,
    isSupportedBottomNav: MutableState<Boolean>
) {
    NavHost(navController = navHostController, startDestination = Screens.AUTH_SCREEN.route) {
        composable(route = Screens.AUTH_SCREEN.route) {
            isSupportedBottomNav.value = false
            jcMapView.onStop()
            AuthorizationScreen(navHostController)
        }
        composable(route = Screens.REG_SCREEN.route) {
            isSupportedBottomNav.value = false
            jcMapView.onStop()
            RegistrationScreen(navHostController)
        }
        composable(route = Screens.PROFILE_SCREEN.route) {
            isSupportedBottomNav.value = true
            jcMapView.onStop()
            ProfileScreen()
        }
        composable(route = Screens.SPORT_COURT_MAP_SCREEN.route) {
            isSupportedBottomNav.value = true
            jcMapView.onStart()
            SportCourtMapScreen(navHostController, jcMapView)
        }
        composable(route = Screens.SPORT_COURT_LIST_SCREEN.route) {
            isSupportedBottomNav.value = true
            jcMapView.onStop()
            SportCourtsListScreen(navHostController)
        }
        composable(route = Screens.TRACK_LIST_SCREEN.route) {
            isSupportedBottomNav.value = true
            jcMapView.onStop()
            RunningTracksListScreen(navHostController)
        }
        composable(route = Screens.WATCH_TRACK_SCREEN.route + "/{trackInfoNumber}",
            arguments = listOf(
                navArgument("trackInfoNumber") {
                    type = NavType.IntType
                }
            )) { entry ->
            isSupportedBottomNav.value = true
            jcMapView.onStart()

/*            WatchTrackScreen(
                navHostController,
                viewModel.tracks.value.runningTracks.first{
                    it.trackId == (entry.arguments?.getInt("trackInfoNumber")
                    ?: -1)
                }
            )*/
        }
        composable(route = Screens.CREATE_TRACK_SCREEN.route) {
            isSupportedBottomNav.value = true
            jcMapView.onStop()
            CreateTrackScreen()
        }
    }
}
