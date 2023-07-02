package ru.riders.sportfinder.screen.setting_screens.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Switch
import androidx.compose.material.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import ru.riders.sportfinder.screen.ui.theme.SportFinderLightColorScheme

@Composable
fun SettingCheckboxItem(
    title: String,
    initialState: Boolean,
    updateCheckState: (Boolean) -> Unit
) {
    var switcherState by remember { mutableStateOf(initialState) }
    Row {
        Box(
            modifier = Modifier
                .align(CenterVertically)
                .weight(1f)
        ) {
            Text(
                modifier = Modifier
                    .align(Alignment.CenterStart),
                text = title
            )
        }
        Switch(
            checked = switcherState,
            onCheckedChange = {
                switcherState = it
                updateCheckState(it)
            },
            colors = SwitchDefaults.colors(
                checkedTrackColor = SportFinderLightColorScheme.primary,
                checkedThumbColor = SportFinderLightColorScheme.secondary
            )
        )
    }
}

@Preview
@Composable
fun PreviewSettingCheckboxItem() {
    SettingCheckboxItem(title = "Edit profile", true, {})
}
