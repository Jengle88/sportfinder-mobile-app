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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import ru.riders.sportfinder.R
import ru.riders.sportfinder.common.Constants
import ru.riders.sportfinder.screen.common_components.DefaultGoogleMap
import ru.riders.sportfinder.screen.common_components.TopSearchBar
import ru.riders.sportfinder.screen.ui.theme.SportFinderLightColorScheme

@Composable
fun SportCourtMapScreen(
    navigateToSportCourtListScreen: () -> Unit,
    viewModel: SportCourtsMapViewModel = hiltViewModel()
) {
    val courtsInfo = viewModel.listSportCourts.value

    var textForFilter = ""

    val context = LocalContext.current
    val cameraPosition = rememberCameraPositionState().apply {
        position = CameraPosition.fromLatLngZoom(Constants.SPB_CENTER_POINT, 15.0f)
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        DefaultGoogleMap(
            modifier = Modifier
                .fillMaxSize(),
            cameraPositionState = cameraPosition
        ) {
            if (courtsInfo.isNotEmpty()) {
                courtsInfo.forEach {
                    Marker(
                        state = MarkerState(it.coordinates),
                        title = "Id: ${it.courtId}",
                        icon = BitmapDescriptorFactory
                            .fromBitmap(
                                ContextCompat.getDrawable(
                                    context,
                                    R.drawable.ic_location_green_24
                                )!!.toBitmap()
                            )
                    )
                }
            }
        }
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
