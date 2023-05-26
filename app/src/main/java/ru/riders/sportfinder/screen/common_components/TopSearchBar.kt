package ru.riders.sportfinder.screen.common_components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension
import ru.riders.sportfinder.R
import ru.riders.sportfinder.screen.ui.theme.LightGreen
import ru.riders.sportfinder.screen.ui.theme.SportFinderLightColorScheme
import ru.riders.sportfinder.screen.ui.theme.White

@Composable
fun TopSearchBar(onTextSearchChanged: (String) -> Unit) {
    var searchText by remember { mutableStateOf("") }

    val constraintsTopSearch = ConstraintSet {
        val searchTextField = createRefFor("searchTextField")
        val spacer = createRefFor("spacer")
        val searchButton = createRefFor("searchButton")

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
            end.linkTo(searchButton.start)
        }
        constrain(searchButton) {
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
            .padding(top = 4.dp, start = 8.dp, end = 8.dp)
    ) {

        androidx.compose.material.TextField(
            modifier = Modifier
                .fillMaxWidth()
                .layoutId("searchTextField")
                .height(48.dp),
            value = searchText,//
            textStyle = TextStyle(
                color = White,
                fontSize = 14.sp),
            onValueChange = { newFilter: String ->
                searchText = newFilter
            },

            shape = RoundedCornerShape(10),
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = LightGreen,
                focusedIndicatorColor = White
            ),
            singleLine = true
        )


        IconButton(
            modifier = Modifier
                .background(SportFinderLightColorScheme.primary)
                .layoutId("searchButton")
                .fillMaxHeight(),
            onClick = {
                onTextSearchChanged(searchText)
            }
        ) {
            Icon(
                modifier = Modifier.fillMaxSize(),
                painter = painterResource(id = R.drawable.ic_search_white_24),
                tint = SportFinderLightColorScheme.onPrimary,
                contentDescription = null
            )
        }
    }
}

@Composable
@Preview
fun TopSearchBarPreview() {
    Box {
        TopSearchBar({ _ -> })
    }
}

