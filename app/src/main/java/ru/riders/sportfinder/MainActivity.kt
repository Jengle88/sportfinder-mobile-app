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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.ramcosta.composedestinations.rememberNavHostEngine
import com.yandex.mapkit.MapKitFactory
import dagger.hilt.android.AndroidEntryPoint
import ru.riders.sportfinder.model.BottomNavItem
import ru.riders.sportfinder.screen.AuthorizationScreen
import ru.riders.sportfinder.screen.CreateTrackScreen
import ru.riders.sportfinder.screen.ProfileScreen
import ru.riders.sportfinder.screen.RegistrationScreen
import ru.riders.sportfinder.screen.Screens
import ru.riders.sportfinder.screen.SportCourtMapScreen
import ru.riders.sportfinder.screen.SportsCourtListScreen
import ru.riders.sportfinder.screen.TrackListScreen
import ru.riders.sportfinder.screen.WatchTrackScreen
import ru.riders.sportfinder.ui.theme.SportFinderLightColorScheme
import ru.riders.sportfinder.ui.theme.SportFinderTheme
import javax.inject.Inject
import javax.inject.Named

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    @Named("YANDEX_MAP_APIKEY")
    lateinit var YANDEX_MAP_APIKEY: String

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MapKitFactory.setApiKey(YANDEX_MAP_APIKEY)
        MapKitFactory.initialize(this)


        setContent {
            val viewModel = hiltViewModel<MainActivityViewModel>()

            val isSupportedBottomNav = remember { mutableStateOf(false) }

            val engine = rememberNavHostEngine()
            val navHostController = engine.rememberNavController()

            val bottomItems = listOf(
                BottomNavItem(
                    painterResource(R.drawable.ic_account_white_24)
                ) { it.navigate("${Screens.PROFILE_SCREEN.route}/${viewModel.userId}}") },
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
    }

    override fun onStop() {
        MapKitFactory.getInstance().onStop()
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
    navHostController: NavHostController,
    isSupportedBottomNav: MutableState<Boolean>
) {
    NavHost(navController = navHostController, startDestination = Screens.AUTH_SCREEN.route) {
        composable(route = Screens.AUTH_SCREEN.route) {
            val viewModel = hiltViewModel<MainActivityViewModel>()
            isSupportedBottomNav.value = false
            AuthorizationScreen(viewModel, navHostController)
        }
        composable(route = Screens.REG_SCREEN.route) {
            val viewModel = hiltViewModel<MainActivityViewModel>()
            isSupportedBottomNav.value = false
            RegistrationScreen(viewModel, navHostController)
        }
        composable(
            route = Screens.PROFILE_SCREEN.route + "/{personId}",
            arguments = listOf(
                navArgument("personId") {
                    type = NavType.StringType
                }
            )
        ) { entry ->
            isSupportedBottomNav.value = true
            ProfileScreen(personId = entry.arguments?.getString("personId") ?: "")
        }
        composable(route = Screens.SPORT_COURT_MAP_SCREEN.route) {
            val viewModel = hiltViewModel<MainActivityViewModel>()
            isSupportedBottomNav.value = true
            SportCourtMapScreen(viewModel, navHostController)
        }
        composable(route = Screens.SPORT_COURT_LIST_SCREEN.route) {
            val viewModel = hiltViewModel<MainActivityViewModel>()
            isSupportedBottomNav.value = true
            viewModel.loadSportCourtsList()
            SportsCourtListScreen(viewModel, navHostController)
        }
        composable(route = Screens.TRACK_LIST_SCREEN.route) {
            val viewModel = hiltViewModel<MainActivityViewModel>()
            isSupportedBottomNav.value = true
            viewModel.loadTrackListMock()
            TrackListScreen(viewModel, navHostController)
        }
        composable(route = Screens.WATCH_TRACK_SCREEN.route + "/{trackInfoNumber}",
            arguments = listOf(
                navArgument("trackInfoNumber") {
                    type = NavType.IntType
                }
            )) { entry ->
            val viewModel = hiltViewModel<MainActivityViewModel>()
            isSupportedBottomNav.value = true
            if (viewModel.tracks.value.isEmpty()) viewModel.loadTrackListMock()

            WatchTrackScreen(
                viewModel,
                navHostController,
                viewModel.tracks.value.first{
                    it.trackId == (entry.arguments?.getInt("trackInfoNumber")
                    ?: -1)
                }
            )
        }
        composable(route = Screens.CREATE_TRACK_SCREEN.route) {
            val viewModel = hiltViewModel<MainActivityViewModel>()
            isSupportedBottomNav.value = true
            CreateTrackScreen(viewModel)
        }
    }
}
