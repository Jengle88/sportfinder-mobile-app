package ru.riders.sportfinder.screen.watch_running_track_screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.yandex.mapkit.map.CameraPosition
import ru.riders.sportfinder.R
import ru.riders.sportfinder.domain.model.running_track.RunningTrack
import ru.riders.sportfinder.screen.common_components.JCMapView
import ru.riders.sportfinder.screen.ui.theme.LightGreen


@Composable
fun WatchRunningTrackScreen(
    navHostController: NavHostController?,
    jcMapView: JCMapView,
    runningTrackId: Int,
    viewModel: WatchRunningTracksViewModel = hiltViewModel()
//    runningTrackDto: RunningTrackDto
) {
    val (name, distance, tempOnStart, tags, points, tempOnEnd) = viewModel.runningTrack.value ?: RunningTrack(
        "", 0.0, 0, "", emptyList(), 0, 0
    )

    jcMapView.drawRunningTrack()

    Column {
        AndroidView(
            modifier = Modifier
                .fillMaxSize(),
            factory = { _ ->
                jcMapView.prepareForNewStart()
                jcMapView.onStart()
                jcMapView.apply {
                    map.move(CameraPosition(viewModel.centerSPbPoint, 15.0f, 0f, 0f))
                }
                jcMapView
            },
            onReset = {
                it.onStop()
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
