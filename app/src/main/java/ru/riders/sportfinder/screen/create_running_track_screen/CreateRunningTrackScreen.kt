package ru.riders.sportfinder.screen.create_running_track_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Checkbox
import androidx.compose.material.CheckboxDefaults
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
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import ru.riders.sportfinder.screen.common_components.JCMapView
import ru.riders.sportfinder.screen.ui.theme.LightGreen
import ru.riders.sportfinder.screen.ui.theme.SportFinderLightColorScheme


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CreateRunningTrackScreen(
    jcMapView: JCMapView,
    viewModel: CreateRunningTrackViewModel = hiltViewModel()
) {
    val lifecycleOwner = rememberUpdatedState(newValue = LocalLifecycleOwner.current)

    val isAlertDialogShow = remember { mutableStateOf(false) }

    DisposableEffect(key1 = true) {
        val lifecycle = lifecycleOwner.value.lifecycle

        jcMapView.attachToLifecycle(lifecycle)

        onDispose {
            jcMapView.detachFromLifecycle(lifecycle)
        }
    }

    SelectTagsAlertDialog(isShown = isAlertDialogShow, viewModel = viewModel)

    Box {
        AndroidView(modifier = Modifier,
            factory = {
                viewModel.initMapView(jcMapView)
            })

        Row (
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .clip(RoundedCornerShape(8.dp))
                .align(Alignment.BottomCenter)
                .background(Color.White.copy(alpha = 0.5f)),
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
                    onClick = { isAlertDialogShow.value = true }
                ) {
                    Text(text = "Добавить теги", color = SportFinderLightColorScheme.onPrimary)
                }
                Button(
                    modifier = Modifier
                        .fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(backgroundColor = LightGreen),
                    onClick = {
                        // TODO: сделать сохранение
                    }
                ) {
                    Text(text = "Сохранить", color = SportFinderLightColorScheme.onPrimary)
                }
            }
        }
    }
}

@Composable
fun SelectTagsAlertDialog(
    isShown: MutableState<Boolean>,
    viewModel: CreateRunningTrackViewModel
) {
    val checkedList = Array(viewModel.listOfTags.value.size) { mutableStateOf(false) }

    if (isShown.value) {
        Dialog(onDismissRequest = { isShown.value = false }) {
            LazyColumn(
                modifier = Modifier
                    .background(Color.White)
                    .padding(16.dp)
            ) {
                for ((i, item) in viewModel.listOfTags.value.withIndex()) {
                    item {
                        Row(
                            modifier = Modifier.clickable { checkedList[i].value = !checkedList[i].value },
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Checkbox(
                                checked = checkedList[i].value,
                                colors = CheckboxDefaults.colors(checkedColor = SportFinderLightColorScheme.primary),
                                onCheckedChange = { checkedList[i].value = it }
                            )
                            Text(item.tag, fontSize = 22.sp)
                        }
                    }
                }
                item {
                    Button(
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(backgroundColor = LightGreen),
                        onClick = {
                        for ((i, isChecked) in checkedList.withIndex()) {
                            if (isChecked.value) {
                                viewModel.addTagToList(viewModel.listOfTags.value[i])
                            }
                        }
                        isShown.value = false
                    }) {
                        Text("Сохранить", color = SportFinderLightColorScheme.onPrimary)
                    }
                }
            }
        }
    }
}