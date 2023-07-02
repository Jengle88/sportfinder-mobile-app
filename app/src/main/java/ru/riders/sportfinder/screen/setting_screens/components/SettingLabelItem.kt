package ru.riders.sportfinder.screen.setting_screens.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.riders.sportfinder.screen.ui.theme.SportFinderLightColorScheme

@Composable
fun SettingLabelItem(
    title: String,
    isDangerous: Boolean,
    action: () -> Unit = {},
) {
    Box(
        modifier = Modifier
            .padding(vertical = 8.dp)
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterStart)
                .clickable { action() },
            color = if (isDangerous) SportFinderLightColorScheme.onError
                    else SportFinderLightColorScheme.onSurface,
            text = title
        )
    }
}

@Preview
@Composable
fun PreviewSettingLabelItem() {
    SettingLabelItem(title = "Edit profile", isDangerous = true, action = {})
}