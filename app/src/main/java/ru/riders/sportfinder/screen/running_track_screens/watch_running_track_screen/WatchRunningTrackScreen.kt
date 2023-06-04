package ru.riders.sportfinder.screen.running_track_screens.watch_running_track_screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import com.yandex.mapkit.Animation
import ru.riders.sportfinder.R
import ru.riders.sportfinder.domain.model.running_track.RunningTrackVO
import ru.riders.sportfinder.screen.common_components.JCMapView
import ru.riders.sportfinder.screen.ui.theme.LightGreen


@Composable
fun WatchRunningTrackScreen(
    jcMapView: JCMapView,
    viewModel: WatchRunningTracksViewModel = hiltViewModel()
) {
    val (name, distance, tempOnStart, tags, points, tempOnEnd) = viewModel.runningTrackVO.value
        ?: RunningTrackVO("", 0.0, 0, "", emptyList(), 0, 0)

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

    if (viewModel.runningTrackVO.value?.points?.isNotEmpty() == true) {
        jcMapView.drawRunningTrack(viewModel.runningTrackVO.value?.points ?: emptyList())
        jcMapView.map.move(
            viewModel.getOptimalCameraPosition(),
            Animation(Animation.Type.SMOOTH, 0.5f),
            null
        )
    }

    Column {
        AndroidView(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.8f)
                .padding(8.dp),
            factory = {
                viewModel.initMapView(jcMapView)
            }
        )
        Column(
            modifier = Modifier.padding(top = 8.dp, start = 12.dp, bottom = 4.dp)
        ) {
            val trackIconsAttributesModifier = Modifier.padding(end = 8.dp)
            Row {
                Icon(
                    painter = painterResource(R.drawable.ic_map_marker_white_24),
                    contentDescription = "Map sign",
                    tint = LightGreen,
                    modifier = trackIconsAttributesModifier
                )
                Text(
                    text = "Расстояние: ",
                    style = MaterialTheme.typography.titleMedium,
                )
                Text(
                    text = distance.toString() + "Km",
                    style = MaterialTheme.typography.titleMedium,
                )
            }
            Row {
                Icon(
                    painter = painterResource(R.drawable.ic_route_white_24),
                    contentDescription = "route sign",
                    tint = LightGreen,
                    modifier = trackIconsAttributesModifier
                )
                Text(
                    text = "Дистанция: ",
                    style = MaterialTheme.typography.titleMedium,
                )
                Text(
                    text = distance.toString() + "Km",
                    style = MaterialTheme.typography.titleMedium,
                )
            }
            Row {
                Icon(
                    painter = painterResource(R.drawable.ic_weather_white_24),
                    contentDescription = "Temperature sign",
                    tint = LightGreen,
                    modifier = trackIconsAttributesModifier
                )
                Text(
                    text = "Погода в начальной точке: ",
                    style = MaterialTheme.typography.titleMedium,
                )
                Text(
                    text = if ((tempOnStart ?: 0) > 0) "+" + tempOnStart.toString() + "C"
                    else tempOnStart.toString() + "C",
                    style = MaterialTheme.typography.titleMedium,
                )
            }
            Row {
                Icon(
                    painter = painterResource(R.drawable.ic_weather_white_24),
                    contentDescription = "Temperature sign",
                    tint = LightGreen,
                    modifier = trackIconsAttributesModifier
                )
                Text(
                    text = "Погода в конечной точке: ",
                    style = MaterialTheme.typography.titleMedium,
                )
                Text(
                    text = if ((tempOnEnd ?: 0) > 0) "+" + tempOnEnd.toString() + "C"
                    else tempOnEnd.toString() + "C",
                    style = MaterialTheme.typography.titleMedium,
                )
            }
        }
    }
}
