package ru.riders.sportfinder

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.ramcosta.composedestinations.rememberNavHostEngine
import dagger.hilt.android.AndroidEntryPoint
import ru.riders.sportfinder.screen.common_components.BottomNavItem
import ru.riders.sportfinder.screen.common_components.TopBarType
import ru.riders.sportfinder.screen.navigation.MainScreenNavHost
import ru.riders.sportfinder.screen.navigation.NavigationController
import ru.riders.sportfinder.screen.ui.theme.SportFinderLightColorScheme
import ru.riders.sportfinder.screen.ui.theme.SportFinderTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    val viewModel by viewModels<MainActivityViewModel>()

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val isSupportedBottomNav = remember { mutableStateOf(false) }
            val topBarType = remember { mutableStateOf<TopBarType?>(null) }

            val engine = rememberNavHostEngine()
            val navigationController = NavigationController(engine.rememberNavController())

            val bottomItems = listOf(
                BottomNavItem(
                    painterResource(R.drawable.ic_account_white_24),
                    navigationController::navigateToProfile
                ),
                BottomNavItem(
                    painterResource(R.drawable.ic_location_white_24),
                    navigationController::navigateToSportCourt
                ),
                BottomNavItem(
                    painterResource(R.drawable.ic_runner_white_24),
                    navigationController::navigateToRunningTrack
                ),
            )

            SportFinderTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Scaffold(
                        modifier = Modifier.fillMaxSize(),
                        topBar = {
                            topBarType.value?.let { it.topBar() }
                        },
                        bottomBar = {
                            if (isSupportedBottomNav.value) {
                                BottomBar(bottomItems)
                            }
                        }
                    ) { paddingValues ->
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(paddingValues)
                        ) {
                            MainScreenNavHost(
                                navigationController = navigationController,
                                isSupportedBottomNav = isSupportedBottomNav,
                                topBarType = topBarType,
                                viewModel = viewModel
                            )
                        }
                    }
                }
            }
        }
    }
}


@Composable
private fun BottomBar(
    items: List<BottomNavItem>
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
                    bottomNavItem.onItemClick()
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
