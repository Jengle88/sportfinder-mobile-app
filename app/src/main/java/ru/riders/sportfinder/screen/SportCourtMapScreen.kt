package ru.riders.sportfinder.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavHostController
import ru.riders.sportfinder.MainActivityViewModel
import ru.riders.sportfinder.R
import ru.riders.sportfinder.screen.widget.JCMapView
import ru.riders.sportfinder.screen.widget.TopSearchBar
import ru.riders.sportfinder.ui.theme.SportFinderLightColorScheme

@Composable
fun SportCourtMapScreen(
    viewModel: MainActivityViewModel?,
    navHostController: NavHostController?
) {
    lateinit var mapView: JCMapView

    var textForFilter = ""

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        AndroidView(
            modifier = Modifier
                .fillMaxSize(),
            factory = { context ->
            mapView = JCMapView(
                context,
                { _, _ -> },
                { _, _ -> }
            )
            mapView
        })

        TopSearchBar(onTextSearchChanged = {
            textForFilter = it
        })

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
                navHostController?.navigate(Screens.SPORT_COURT_LIST_SCREEN.route)
            }
        ) {
            Row {
                Icon(painterResource(id = R.drawable.ic_list_bulleted_white_24), null)
                Spacer(modifier = Modifier.width(4.dp))
                Text(text = "Список", color = SportFinderLightColorScheme.onPrimary)
            }
        }

    }
}

@Preview
@Composable
fun SportCourtMapScreenPreview() {
    SportCourtMapScreen(null, null)
}