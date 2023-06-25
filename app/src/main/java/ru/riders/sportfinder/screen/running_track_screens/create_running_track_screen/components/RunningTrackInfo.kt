package ru.riders.sportfinder.screen.running_track_screens.create_running_track_screen.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Chip
import androidx.compose.material.ChipDefaults
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Clear
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import ru.riders.sportfinder.screen.running_track_screens.create_running_track_screen.CreateRunningTrackViewModel
import ru.riders.sportfinder.screen.ui.theme.LightGreen
import ru.riders.sportfinder.screen.ui.theme.SportFinderLightColorScheme


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun RunningTrackInfo(
    modifier: Modifier = Modifier,
    viewModel: CreateRunningTrackViewModel,
    showTagAlertDialog: () -> Unit,
    onSaveClick: () -> Unit
) {
    Row (
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(8.dp)
        ) {
            Text(
                text = "Дистанция: ${"%.2f".format(viewModel.distance.value)}м",
                modifier = Modifier
            )
            TextField(
                value = viewModel.title.value,
                shape = RoundedCornerShape(10),
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.White,
                    focusedIndicatorColor = LightGreen
                ),
                singleLine = true,
                modifier = Modifier
                    .padding(top = 8.dp)
                    .fillMaxWidth(),
                placeholder = {
                    Text(text = "Название маршрута")
                },
                onValueChange = {
                    viewModel.title.value = it
                })
            LazyRow(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                for (tag in viewModel.listOfSelectedTags) {
                    item {
                        Chip(
                            colors = ChipDefaults.chipColors(
                                backgroundColor = SportFinderLightColorScheme.primary,
                                leadingIconContentColor = SportFinderLightColorScheme.onPrimary
                            ),
                            onClick = {},
                            leadingIcon = { Icon(Icons.Outlined.Clear, null, modifier = Modifier.clickable { viewModel.removeTagFromList(tag) }) }
                        ) {
                            Text(tag.tag, color = SportFinderLightColorScheme.onPrimary)
                        }
                    }
                }
            }
            Button(
                modifier = Modifier
                    .fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(backgroundColor = LightGreen),
                onClick = { showTagAlertDialog() }
            ) {
                Text(text = "Добавить теги", color = SportFinderLightColorScheme.onPrimary)
            }
            Button(
                modifier = Modifier
                    .fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(backgroundColor = LightGreen),
                onClick = {
                    onSaveClick()
                }
            ) {
                Text(text = "Сохранить", color = SportFinderLightColorScheme.onPrimary)
            }
        }
    }
}