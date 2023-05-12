package ru.riders.sportfinder.screen.widget

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BoxScope.FilterBar(onTextFilterChanged: (String) -> Unit) {
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
    ConstraintLayout(
        constraintSet = constraintsTopSearch,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp)
            .align(Alignment.TopStart)
    ) {
        TextField(modifier = Modifier
            .fillMaxWidth()
            .layoutId("searchTextField"),
            value = filterText,
            onValueChange = { newFilter ->
                filterText = newFilter
                onTextFilterChanged(newFilter)
            })
    }
}

@Composable
@Preview
fun FilterBarPreview() {
    Box{
        FilterBar({ _ -> })
    }
}

