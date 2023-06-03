package ru.riders.sportfinder.screen.create_running_track_screen

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
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import ru.riders.sportfinder.screen.common_components.JCMapView
import ru.riders.sportfinder.screen.ui.theme.LightGreen
import ru.riders.sportfinder.screen.ui.theme.White


@Composable
fun CreateRunningTrackScreen(
    jcMapView: JCMapView,
    viewModel: CreateRunningTrackViewModel = hiltViewModel()
) {
    var trackName: String by remember { mutableStateOf("") }

    val lifecycleOwner = rememberUpdatedState(newValue = LocalLifecycleOwner.current)

    DisposableEffect(key1 = true) {
        val lifecycle = lifecycleOwner.value.lifecycle

        jcMapView.attachToLifecycle(lifecycle)

        onDispose {
            jcMapView.detachFromLifecycle(lifecycle)
        }
    }

    Box {
        AndroidView(modifier = Modifier,
            factory = { context ->
                viewModel.initMapView(jcMapView)
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
                    text = "Дистанция: ${"%.2f".format(viewModel.distance.value)}м",
                    modifier = Modifier
                )
                TextField(
                    value = viewModel.title.value,
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
                        viewModel.title.value = it
                    })
                Button(
                    onClick = {
                        // TODO: сделать сохранение
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