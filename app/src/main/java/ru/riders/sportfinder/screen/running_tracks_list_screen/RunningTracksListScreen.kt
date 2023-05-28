package ru.riders.sportfinder.screen.running_tracks_list_screen

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
import androidx.hilt.navigation.compose.hiltViewModel
import ru.riders.sportfinder.R
import ru.riders.sportfinder.screen.common_components.TopSearchBar
import ru.riders.sportfinder.screen.running_tracks_list_screen.components.TrackListItem
import ru.riders.sportfinder.screen.ui.theme.SportFinderLightColorScheme

@Composable
fun RunningTracksListScreen(
    navigateToWatchRunningTrackScreen: (Int) -> Unit,
    navigateToCreateTrackScreen: () -> Unit,
    viewModel: RunningTracksListViewModel = hiltViewModel()
) {
    val runningTracks = viewModel.listRunningTracks.value

    var searchedText by remember { mutableStateOf("") }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column {
            TopSearchBar(onTextSearchChanged = { str ->
                searchedText = str
            })

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize(),
                contentPadding = PaddingValues(8.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                val listOfFilteredTracks = if (searchedText.isNotEmpty())
                    runningTracks.filter { searchedText in it.title }
                else
                    runningTracks

                listOfFilteredTracks.forEach { trackInfo ->
                    item {
                        Box(
                            modifier = Modifier.clickable {
                                navigateToWatchRunningTrackScreen(trackInfo.trackId)
                            }
                        ) {
                            TrackListItem(trackInfo)
                        }
                    }
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
                navigateToCreateTrackScreen()
            }
        ) {
            Icon(painterResource(id = R.drawable.ic_add_white_24), null)
        }

    }
}

@Preview
@Composable
fun TrackListScreenPreview() {
    RunningTracksListScreen({}, {})
}