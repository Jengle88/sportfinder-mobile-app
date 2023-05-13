package ru.riders.sportfinder.screen

import android.widget.Toast
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.riders.sportfinder.ui.theme.LightGreen
import ru.riders.sportfinder.ui.theme.White


@Composable
fun CreateTrackScreen() {
    var trackName: String by remember { mutableStateOf("") }
    Column {
//        AndroidView(
//            modifier = Modifier
//                .fillMaxSize(),
//            factory = { context ->
//                mapView = JCMapView(
//                    context,
//                    { _, _ -> },
//                    { _, _ -> }
//                )
//                mapView
//            })
        Column(
            modifier = Modifier.padding(top = 8.dp, start = 12.dp, bottom = 4.dp).fillMaxWidth()
        ) {
            TextField(
                value = trackName,
                shape = RoundedCornerShape(
                    10
                ),
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
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(backgroundColor = LightGreen)
            ) {
                Text(text = "Сохранить", color = White)
            }
        }
    }
}


@Composable
@Preview
fun CreateTrackScreenPreview() {
    CreateTrackScreen()

}