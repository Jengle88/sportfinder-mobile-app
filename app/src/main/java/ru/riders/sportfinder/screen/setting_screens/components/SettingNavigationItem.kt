package ru.riders.sportfinder.screen.setting_screens.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun SettingNavigationItem(
    title: String,
    navigateTo: () -> Unit
) {
    Box(
        modifier = Modifier
            .padding(bottom = 8.dp)
    ) {
       Text(
           modifier = Modifier
               .fillMaxWidth()
               .align(Alignment.CenterStart)
               .padding(end = 30.dp),
           text = title
       )
        IconButton(
            modifier = Modifier
                .size(30.dp)
                .align(Alignment.TopEnd),
            onClick = navigateTo
        ) {
            Icon(
                modifier = Modifier
                    .fillMaxSize(),
                imageVector = Icons.Outlined.KeyboardArrowRight,
                contentDescription = null
            )
        }
    }
}

@Preview
@Composable
fun PreviewSettingNavigationItem() {
    SettingNavigationItem(title = "Edit profile", navigateTo = {})
}
