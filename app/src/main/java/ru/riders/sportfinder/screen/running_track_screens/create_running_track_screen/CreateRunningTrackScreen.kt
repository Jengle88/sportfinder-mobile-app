package ru.riders.sportfinder.screen.running_track_screens.create_running_track_screen

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
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
import ru.riders.sportfinder.screen.common_components.DefaultGoogleMap
import ru.riders.sportfinder.screen.common_components.SelectListAlertDialog
import ru.riders.sportfinder.screen.running_track_screens.create_running_track_screen.components.RunningTrackInfo
import ru.riders.sportfinder.screen.ui.theme.SportFinderLightColorScheme


@Composable
fun CreateRunningTrackScreen(
    navigateToRunningTrackScreen: (Int) -> Unit,
    viewModel: CreateRunningTrackViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val cameraPosition = rememberCameraPositionState().apply {
        position = CameraPosition.fromLatLngZoom(Constants.SPB_CENTER_POINT, 15.0f)
    }
    val isAlertDialogShow = remember { mutableStateOf(false) }

    if (isAlertDialogShow.value) {
        SelectListAlertDialog(
            items = viewModel.listOfTags.value.map { it.tag },
            onSaveClick = { result ->
                result.forEach { index ->
                    viewModel.addTagToList(viewModel.listOfTags.value[index])
                }
            },
            onDismiss = {
                isAlertDialogShow.value = false
            }
        )
    }

    Box {
        DefaultGoogleMap(
            modifier = Modifier
                .fillMaxSize(),
            cameraPositionState = cameraPosition,
            onMapClick = viewModel::addPoint,
            onMapLongClick = viewModel::removePoint
        ) {
            if (viewModel.listOfPoints.isNotEmpty()) {
                viewModel.listOfPoints.forEach {
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
                if (viewModel.listOfPoints.size > 1) {
                    Polyline(
                        points = viewModel.listOfPoints.toList(),
                        jointType = JointType.ROUND,
                        color = SportFinderLightColorScheme.primary,
                        width = 12f
                    )
                }
            }
        }
        RunningTrackInfo(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .clip(RoundedCornerShape(8.dp))
                .align(Alignment.BottomCenter)
                .background(Color.White.copy(alpha = 0.5f)),
            viewModel = viewModel,
            showTagAlertDialog = { isAlertDialogShow.value = true },
            onSaveClick = {
                viewModel.saveRunningTrack(
                    onSuccess = { newRouteId ->
                        navigateToRunningTrackScreen(newRouteId)
                    },
                    onFailure = {
                        Toast.makeText(context, "Не удалось сохранить маршрут", Toast.LENGTH_SHORT).show()
                    }
                )
            }
        )
    }
}
