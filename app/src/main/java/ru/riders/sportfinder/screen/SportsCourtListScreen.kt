package ru.riders.sportfinder.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.riders.sportfinder.MainActivityViewModel
import ru.riders.sportfinder.screen.widget.SportCourtListItem
import ru.riders.sportfinder.screen.widget.TopFilterBar
import ru.riders.sportfinder.data.SportCourtInfo
@Composable
fun SportsCourtListScreen(
    viewModel: MainActivityViewModel?,
) {
    val courtsInfo by remember {
        viewModel!!.sportsCourts
    }
    Box(
        modifier = Modifier.fillMaxSize(),

    ) {

        Column (
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ){
            TopFilterBar { _ -> }

            LazyColumn(
                modifier = Modifier.fillMaxWidth(0.8F),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)

            ) {
                courtsInfo.forEach {
                    item{
                        SportCourtListItem(
                            name = it.name,
                            tegs = it.tegs,
                            distance = it.distance,
                            temperature = it.temperature
                        )
                    }
                }
            }
        }

    }
}

@Preview
@Composable
fun SportsCourtListScreenPreview() {
    SportsCourtListScreen(null)
}


