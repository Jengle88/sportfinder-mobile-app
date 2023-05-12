package ru.riders.sportfinder.screen

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import ru.riders.sportfinder.MainActivityViewModel
import ru.riders.sportfinder.R
import ru.riders.sportfinder.screen.widget.SportCourtListItem



@Composable
fun SportsCourtListScreen(
        viewModel: MainActivityViewModel?,
) {
    val courtsInfo = getMock()

    LazyColumn(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        courtsInfo.forEach {
            item {
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

@Preview
@Composable
fun SportsCourtListScreenPreview() {
    SportsCourtListScreen(null)
}
data class CourtInfo(
                val name: String,
                val tegs: List<String>? = null,
                val distance: Float? = null,
                val temperature: Float? = null,
                val resourceId: Painter? = null)
fun getMock(): List<CourtInfo>{
    return listOf(
            CourtInfo(
                    name = "Девяткино",
                    tegs = listOf("одна дорога на Лаврики", "дыра"),
                    distance = 999.9F,
                    temperature = -666.6F),
            CourtInfo(
                    name = "Старая",
                    tegs = listOf("уют", "шава"),
                    distance = 1.3F,
                    temperature = 24.6F),
            CourtInfo(
                    name = "Новая",
                    tegs = listOf("новая", "без шавы"),
                    distance = 0.3F,
                    temperature = 21.2F),
    )
}