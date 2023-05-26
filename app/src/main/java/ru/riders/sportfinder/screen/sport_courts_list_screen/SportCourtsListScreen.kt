package ru.riders.sportfinder.screen.sport_courts_list_screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material3.Text
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
import androidx.navigation.NavHostController
import ru.riders.sportfinder.R
import ru.riders.sportfinder.screen.Screens
import ru.riders.sportfinder.screen.common_components.TopSearchBar
import ru.riders.sportfinder.screen.sport_courts_list_screen.components.SportCourtListItem
import ru.riders.sportfinder.screen.ui.theme.SportFinderLightColorScheme

@Composable
fun SportCourtsListScreen(
    navHostController: NavHostController?,
    viewModel: SportCourtListViewModel = hiltViewModel()
) {
    val listSportCourts = viewModel.listSportCourts.value

    var searchedText by remember { mutableStateOf("") }

    Box(modifier = Modifier.fillMaxSize()) {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            TopSearchBar { str -> searchedText = str }

            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(8.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)

            ) {
                val listOfFilteredSportCourts = if (searchedText.isNotEmpty())
                    listSportCourts.filter { searchedText in it.name }
                else
                    listSportCourts

                items(listOfFilteredSportCourts) {
                    SportCourtListItem(it)
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
            shape = RoundedCornerShape(28.dp),
            onClick = {
                navHostController?.navigate(Screens.SPORT_COURT_MAP_SCREEN.route)
            }
        ) {
            Row {
                Icon(painterResource(id = R.drawable.ic_map_white_24), null)
                Spacer(modifier = Modifier.width(4.dp))
                Text(text = "Карта", color = SportFinderLightColorScheme.onPrimary)
            }
        }

    }
}

@Preview
@Composable
fun SportsCourtListScreenPreview() {
    SportCourtsListScreen(null)
}


