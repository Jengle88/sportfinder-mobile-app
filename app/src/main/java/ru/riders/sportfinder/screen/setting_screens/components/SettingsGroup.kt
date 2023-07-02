package ru.riders.sportfinder.screen.setting_screens.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.riders.sportfinder.screen.ui.theme.SportFinderLightColorScheme

@Composable
fun SettingGroup(
    groupTitle: String,
    withDivider: Boolean = false,
    groupContent: @Composable () -> Unit = {}
) {
    Column {
        Column(
            modifier = Modifier
                .padding(8.dp)
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth(),
                text = groupTitle,
                color = SportFinderLightColorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(16.dp))

            groupContent()
        }
        if (withDivider) {
            Divider(
                modifier = Modifier
                    .fillMaxWidth()
            )
        }
    }
}

@Preview
@Composable
fun PreviewSettingGroup() {
    SettingGroup(groupTitle = "Common", withDivider = true, groupContent = {
        SettingNavigationItem(title = "Edit profile", navigateTo = {})
        SettingCheckboxItem(title = "Edit profile true", initialState = true, {})
        SettingCheckboxItem(title = "Edit profile false", initialState = false, {})
        SettingLabelItem(title = "Logout", isDangerous = false, action = {})
        SettingLabelItem(title = "Logout", isDangerous = true, action = {})
    })
}
