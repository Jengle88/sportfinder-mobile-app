package ru.riders.sportfinder.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import ru.riders.sportfinder.MainActivityViewModel
import ru.riders.sportfinder.R
import ru.riders.sportfinder.screen.widget.TopSearchBar
import ru.riders.sportfinder.screen.widget.TrackListItem
import ru.riders.sportfinder.ui.theme.SportFinderLightColorScheme

@Composable
fun TrackListScreen(
    viewModel: MainActivityViewModel?,
    navHostController: NavHostController?
) {

    val tracks by remember {
        viewModel?.tracks ?: mutableStateOf(emptySet())
    }

    var textForFilter = ""

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column {
            TopSearchBar(onTextSearchChanged = {
                textForFilter = it
            })

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize(),
                contentPadding = PaddingValues(8.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                var trackNumber = 0
                tracks.forEach { trackInfo ->
                    item {
                        Box (
                            modifier = Modifier.clickable {
                                val localId = trackNumber
                                navHostController?.navigate(
                                    route = Screens.WATCH_TRACK_SCREEN.route
                                            +"/" + (localId-1).toString() )
                            }
                        ){
                            TrackListItem(trackInfo)
                        }
                    }
                    ++trackNumber
                }
            }
        }

        Button(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(end = 16.dp, bottom = 16.dp),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = SportFinderLightColorScheme.primary,
                contentColor = SportFinderLightColorScheme.onPrimary
            ),
            shape = CircleShape,
            onClick = {
                navHostController?.navigate(
                    route = Screens.CREATE_TRACK_SCREEN.route
                )
            }
        ) {
            Icon(painterResource(id = R.drawable.ic_add_white_24), null)
        }

    }
}

@Preview
@Composable
fun TrackListScreenPreview() {
    TrackListScreen(null, null)
}