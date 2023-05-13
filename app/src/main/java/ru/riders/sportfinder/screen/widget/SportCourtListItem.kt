package ru.riders.sportfinder.screen.widget

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.riders.sportfinder.R
import ru.riders.sportfinder.ui.theme.LightGray
import ru.riders.sportfinder.ui.theme.LightGreen


@Composable
fun SportCourtListItem(
        name: String,
        tegs: List<String>?,
        distance: Float?,
        temperature: Float?,
        painter: Painter = painterResource(id = R.drawable.no_image_placeholder)) {
    Box(
            modifier = Modifier
                .border(BorderStroke(2.dp, LightGreen), RoundedCornerShape(5))
                .fillMaxWidth()
    ) {
        Column {
            Row {
                val longImageSide = 100
                Image(
                        painter = painter,
                        contentDescription = "Court image",
                        modifier = Modifier
                                .padding(10.dp)

                                .size(longImageSide.dp, longImageSide.dp * 2 / 3)
                )
                Column(
                        modifier = Modifier.padding(top = 5.dp).fillMaxWidth()
                ) {
                    Text(
                            text = name,
                            style = MaterialTheme.typography.headlineSmall,
                        modifier = Modifier.fillMaxWidth()
                    )
                    tegs?.joinToString(separator = ",")?.let {
                        Text(
                                text = it,
                                style = MaterialTheme.typography.titleMedium,
                                color = LightGray
                        )
                    }

                }

            }
            Row(
                    modifier = Modifier.padding(5.dp)
            ) {
                val courtAttributesModifier = Modifier.padding(end = 10.dp)
                val courtIconsAttributesModifier = Modifier.padding(end = 8.dp)
                distance?.let {
                    Icon(
                            painter = painterResource(R.drawable.ic_map_marker_white_24),
                            contentDescription = "Map sign",
                            tint = LightGreen,
                            modifier = courtIconsAttributesModifier
                    )
                    Text(
                            text = distance.toString() + "Km",
                            modifier = courtAttributesModifier,
                            style = MaterialTheme.typography.titleMedium,
                    )
                }

                temperature?.let {
                    Icon(
                            painter = painterResource(R.drawable.ic_weather_white_24),
                            contentDescription = "Temperature sign",
                            tint = LightGreen,
                            modifier = courtIconsAttributesModifier

                    )

                    Text(
                            text = if(temperature > 0 ) "+" + temperature.toString() + "C"
                                    else temperature.toString() + "C",
                            modifier = courtAttributesModifier,
                            style = MaterialTheme.typography.titleMedium,
                    )
                }

            }
        }
    }

}

@Composable
@Preview
fun SportCourtListItemPreview() {
    SportCourtListItem(name = "Старая", tegs = listOf("Вкусно", "Кушать", "Нажор"), distance = 0.3F, temperature = 13.4F)
}