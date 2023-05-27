package ru.riders.sportfinder.screen.running_tracks_list_screen.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.riders.sportfinder.R
import ru.riders.sportfinder.domain.model.running_track.RunningTrackForList
import ru.riders.sportfinder.screen.ui.theme.LightGray
import ru.riders.sportfinder.screen.ui.theme.LightGreen

@Composable
fun TrackListItem(runningTrackDto: RunningTrackForList) {
    val (trackId, title, distance, tags, tempOnStart) = runningTrackDto

    Box(
        modifier = Modifier
            .border(BorderStroke(2.dp, LightGreen), RoundedCornerShape(5))
            .fillMaxWidth()
    ) {
        Column {
            Column(
                modifier = Modifier
                    .padding(top = 12.dp, start = 12.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.headlineSmall
                )
                Text(
                    text = tags,
                    style = MaterialTheme.typography.titleMedium,
                    color = LightGray
                )

            }
            Row(
                modifier = Modifier.padding(top = 8.dp, start = 12.dp, bottom = 4.dp)
            ) {
                val trackAttributesModifier = Modifier.padding(end = 10.dp)
                val trackIconsAttributesModifier = Modifier.padding(end = 8.dp)

                Icon(
                    painter = painterResource(R.drawable.ic_map_marker_white_24),
                    contentDescription = "Map sign",
                    tint = LightGreen,
                    modifier = trackIconsAttributesModifier
                )
                Text(
                    text = distance.toString() + "Km",
                    modifier = trackAttributesModifier,
                    style = MaterialTheme.typography.titleMedium,
                )

                Icon(
                    painter = painterResource(R.drawable.ic_route_white_24),
                    contentDescription = "route sign",
                    tint = LightGreen,
                    modifier = trackIconsAttributesModifier
                )
                Text(
                    text = distance.toString() + "Km",
                    modifier = trackAttributesModifier,
                    style = MaterialTheme.typography.titleMedium,
                )

                Icon(
                    painter = painterResource(R.drawable.ic_weather_white_24),
                    contentDescription = "Temperature sign",
                    tint = LightGreen,
                    modifier = trackIconsAttributesModifier
                )
                Text(
                    text = if((tempOnStart ?: 0) > 0 ) "+" + tempOnStart.toString() + "C"
                    else tempOnStart.toString() + "C",
                    modifier = trackAttributesModifier,
                    style = MaterialTheme.typography.titleMedium,
                )

            }
        }
    }
}

@Preview
@Composable
fun TrackListItemPreview() {
    val i = 1
    TrackListItem(
        RunningTrackForList(
            0,
            "Title $i",
            i * 100.toDouble(),
            "tag ${i + 1}",
                i + 10
        )
    )
}