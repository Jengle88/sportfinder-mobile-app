package ru.riders.sportfinder.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.yandex.mapkit.map.CameraPosition
import ru.riders.sportfinder.MainActivityViewModel
import ru.riders.sportfinder.screen.commonComponents.JCMapView
import ru.riders.sportfinder.screen.ui.theme.LightGreen
import ru.riders.sportfinder.screen.ui.theme.White


@Composable
fun CreateTrackScreen(
    viewModel: MainActivityViewModel?
) {
    var trackName: String by remember { mutableStateOf("") }
    lateinit var mapView: JCMapView

    Box {
        AndroidView(modifier = Modifier,
            factory = { context ->
                mapView = JCMapView(
                    context
                ).apply {
                    viewModel?.let { viewModel ->
                        map.move(
                            CameraPosition(viewModel.centerSPbPoint, 15.0f, 0f, 0f)
                        )
                    }
                }
                mapView
            })

        Row (
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .clip(RoundedCornerShape(8.dp))
                .align(Alignment.BottomCenter)
                .background(Color.White.copy(alpha = 0.5f)),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(8.dp)
            ) {
                Text(
                    text = "Дистанция: 0км",
                    modifier = Modifier
                )
                TextField(
                    value = trackName,
                    shape = RoundedCornerShape(10),
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = Color.White,
                        focusedIndicatorColor = LightGreen
                    ),
                    singleLine = true,
                    modifier = Modifier
                        .padding(top = 8.dp)
                        .fillMaxWidth(),
                    placeholder = {
                        Text(text = "Название маршрута")
                    },
                    onValueChange = {
                        trackName = it
                    })
                Button(
                    onClick = {

                    },
                    modifier = Modifier
                        .fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(backgroundColor = LightGreen)
                ) {
                    Text(text = "Сохранить", color = White)
                }

            }
        }
    }
}


@Composable
@Preview
fun CreateTrackScreenPreview() {
    CreateTrackScreen(null)

}