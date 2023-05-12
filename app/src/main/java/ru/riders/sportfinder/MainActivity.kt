package ru.riders.sportfinder

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.ramcosta.composedestinations.rememberNavHostEngine
import com.yandex.mapkit.MapKitFactory
import dagger.hilt.android.AndroidEntryPoint
import ru.riders.sportfinder.ui.theme.SportFinderTheme
import javax.inject.Inject
import javax.inject.Named

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    @Named("YANDEX_MAP_APIKEY")
    lateinit var YANDEX_MAP_APIKEY: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MapKitFactory.setApiKey(YANDEX_MAP_APIKEY)
        MapKitFactory.initialize(this)

        setContent {
            val engine = rememberNavHostEngine()
            val navHostController = engine.rememberNavController()

            SportFinderTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreenNavHost(navHostController = navHostController).apply {
                        navHostController.navigate("main/Hello World")
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
fun MainScreen(
    text: String
) {
    Box (modifier = Modifier.fillMaxSize()) {
        Text(
            modifier = Modifier.align(Alignment.Center),
            text = text
        )
    }
}

@Composable
fun MainScreenNavHost(
    navHostController: NavHostController
) {
    NavHost(navController = navHostController, startDestination = "main/{text}") {
        composable(
            "main/{text}",
            arguments = listOf(
                navArgument("text") {
                    type = NavType.StringType
                    defaultValue = "Error"
                }
            )
        ) { entry ->
            MainScreen(text = entry.arguments?.getString("text") ?: "text")
        }
    }
}
