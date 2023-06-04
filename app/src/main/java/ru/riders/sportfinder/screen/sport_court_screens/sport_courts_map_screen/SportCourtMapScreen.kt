package ru.riders.sportfinder.screen.sport_court_screens.sport_courts_map_screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import com.yandex.mapkit.map.CameraPosition
import ru.riders.sportfinder.R
import ru.riders.sportfinder.screen.common_components.JCMapView
import ru.riders.sportfinder.screen.common_components.TopSearchBar
import ru.riders.sportfinder.screen.ui.theme.SportFinderLightColorScheme

@Composable
fun SportCourtMapScreen(
    jcMapView: JCMapView,
    navigateToSportCourtListScreen: () -> Unit,
    viewModel: SportCourtsMapViewModel = hiltViewModel()
) {
    val courtsInfo = viewModel.listSportCourts.value

    var textForFilter = ""

    val lifecycleOwner = rememberUpdatedState(newValue = LocalLifecycleOwner.current)

    // привязывает ЖЦ карт к ЖЦ экрана, выполняется один раз при старте экрана
    DisposableEffect(key1 = true) {
        // ЖЦ экрана для наблюдения
        val lifecycle = lifecycleOwner.value.lifecycle

        jcMapView.attachToLifecycle(lifecycle)

        onDispose {
            // удаляем наблюдателя
            jcMapView.detachFromLifecycle(lifecycle)
        }
    }

    if (courtsInfo.isNotEmpty()) {
        jcMapView.apply {
            courtsInfo.forEach { addPoint(it.coordinates) }
        }
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        AndroidView(
            modifier = Modifier
                .fillMaxSize(),
            factory = { context ->
                jcMapView.apply {
                    map.move(CameraPosition(viewModel.centerSPbPoint, 15.0f, 0f, 0f))
                }
            }
        )

        TopSearchBar(onTextSearchChanged = {
            textForFilter = it
        })

        Button(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(end = 16.dp, bottom = 16.dp),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = SportFinderLightColorScheme.primary,
                contentColor = SportFinderLightColorScheme.onPrimary
            ),
            shape = RoundedCornerShape(28.dp),
            onClick = {
                navigateToSportCourtListScreen()
            }
        ) {
            Row {
                Icon(painterResource(id = R.drawable.ic_list_bulleted_white_24), null)
                Spacer(modifier = Modifier.width(4.dp))
                Text(text = "Список", color = SportFinderLightColorScheme.onPrimary)
            }
        }
    }
}
