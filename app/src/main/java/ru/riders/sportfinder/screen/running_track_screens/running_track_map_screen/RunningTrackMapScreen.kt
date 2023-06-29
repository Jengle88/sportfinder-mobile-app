package ru.riders.sportfinder.screen.running_track_screens.running_track_map_screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.JointType
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.Polyline
import com.google.maps.android.compose.rememberCameraPositionState
import ru.riders.sportfinder.R
import ru.riders.sportfinder.common.Constants
import ru.riders.sportfinder.domain.model.running_track.RunningTrackVO
import ru.riders.sportfinder.screen.common_components.DefaultGoogleMap
import ru.riders.sportfinder.screen.ui.theme.LightGreen
import ru.riders.sportfinder.screen.ui.theme.SportFinderLightColorScheme


@Composable
fun RunningTrackMapScreen(
    viewModel: RunningTrackMapViewModel = hiltViewModel()
) {
    val (name, distance, tempOnStart, tags, points, tempOnEnd) = viewModel.runningTrackVO.value
        ?: RunningTrackVO("", 0.0, 0, "", emptyList(), 0, 0)

    val context = LocalContext.current
    val cameraPositionState = rememberCameraPositionState().apply {
        position = CameraPosition.fromLatLngZoom(Constants.SPB_CENTER_POINT, 15.0f)
    }

    Column {
        DefaultGoogleMap(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.8f)
                .padding(8.dp),
            cameraPositionState = cameraPositionState
        ) {
            if (points?.isNotEmpty() == true) {
                cameraPositionState.position = CameraPosition.fromLatLngZoom(points.first(), 15.0f)
                points.forEach {
                    Marker(
                        state = MarkerState(it),
                        anchor = Offset(0.5f, 0.5f),
                        icon = BitmapDescriptorFactory
                            .fromBitmap(
                                ContextCompat.getDrawable(
                                    context,
                                    R.drawable.ic_placemark_point
                                )!!.toBitmap()
                            ),
                        flat = true
                    )
                }
                if (points.size > 1) {
                    Polyline(
                        points = points.toList(),
                        jointType = JointType.ROUND,
                        color = SportFinderLightColorScheme.primary,
                        width = 12f
                    )
                }
            }
        }
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
