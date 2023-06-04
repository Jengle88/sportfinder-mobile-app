package ru.riders.sportfinder.screen.running_track_screens.create_running_track_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import ru.riders.sportfinder.screen.common_components.JCMapView
import ru.riders.sportfinder.screen.common_components.SelectListAlertDialog
import ru.riders.sportfinder.screen.running_track_screens.create_running_track_screen.components.RunningTrackInfo


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

    if (isAlertDialogShow.value) {
        SelectListAlertDialog(
            items = viewModel.listOfTags.value.map { it.tag },
            onSaveClick = { result ->
                result.forEach { index ->
                    viewModel.addTagToList(viewModel.listOfTags.value[index])
                }
            },
            onDismiss = {
                isAlertDialogShow.value = false
            }
        )
    }

    Box {
        AndroidView(modifier = Modifier,
            factory = {
                viewModel.initMapView(jcMapView)
            })

        RunningTrackInfo(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .clip(RoundedCornerShape(8.dp))
                .align(Alignment.BottomCenter)
                .background(Color.White.copy(alpha = 0.5f)),
            viewModel = viewModel,
            showTagAlertDialog = { isAlertDialogShow.value = true }
        )
    }
}
