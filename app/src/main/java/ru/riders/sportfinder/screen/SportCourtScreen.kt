package ru.riders.sportfinder.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.TextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension
import ru.riders.sportfinder.R
import ru.riders.sportfinder.screen.widget.JCMapView
import ru.riders.sportfinder.ui.theme.SportFinderLightColorScheme

@Composable
fun SportCourtScreen() {
    lateinit var mapView: JCMapView

    var filterText by remember { mutableStateOf("") }

    val constraintsTopSearch = ConstraintSet {
        val searchTextField = createRefFor("searchTextField")
        val spacer = createRefFor("spacer")
        val filterButton = createRefFor("filterButton")

        constrain(searchTextField) {
            top.linkTo(parent.top)
            start.linkTo(parent.start)
            end.linkTo(spacer.start)
            width = Dimension.fillToConstraints
            height = Dimension.wrapContent
        }
        constrain(spacer) {
            width = Dimension.value(4.dp)
            top.linkTo(parent.top)
            end.linkTo(filterButton.start)
        }
        constrain(filterButton) {
            top.linkTo(parent.top)
            bottom.linkTo(parent.bottom)
            end.linkTo(parent.end)
            width = Dimension.value(48.dp)
            height = Dimension.value(48.dp)
        }
    }

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

        ConstraintLayout(
            constraintSet = constraintsTopSearch,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp)
                .align(Alignment.TopStart)
        ) {
            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .layoutId("searchTextField")
                ,
                value = filterText,
                onValueChange = { newFilter -> filterText = newFilter}
            )

            IconButton(
                modifier = Modifier
                    .background(SportFinderLightColorScheme.primary)
                    .layoutId("filterButton"),
                onClick = { }
            ) {
                Icon(
                    modifier = Modifier.fillMaxSize(),
                    painter = painterResource(id = R.drawable.ic_filter_white_24),
                    tint = SportFinderLightColorScheme.onPrimary,
                    contentDescription = null
                )
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
            onClick = {}
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
fun SportCourtScreenPreview() {
    SportCourtScreen()
}