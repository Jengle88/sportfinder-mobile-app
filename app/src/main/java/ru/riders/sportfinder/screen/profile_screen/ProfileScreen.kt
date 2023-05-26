package ru.riders.sportfinder.screen.profile_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Tab
import androidx.compose.material3.Icon
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import ru.riders.sportfinder.R
import ru.riders.sportfinder.screen.profile_screen.components.TabNavItem
import ru.riders.sportfinder.screen.ui.theme.SportFinderLightColorScheme

@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel = hiltViewModel()
) {
    val (tabIndex, profileName) = viewModel.state.value

    val tabs = listOf(
        TabNavItem(
            painterResource(id = R.drawable.ic_favorite_inactive_white_24),
            onTabClick = {  }
        ),
        TabNavItem(
            painterResource(id = R.drawable.ic_bookmark_white_24),
            onTabClick = {  }
        ),
        TabNavItem(
            painterResource(id = R.drawable.ic_notifications_white_24),
            onTabClick = {  }
        )
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Start
            ) {
                Image(
                    painter = painterResource(id = R.drawable.sportfinder_logo),
                    modifier = Modifier
                        .size(100.dp, 100.dp)
                        .clip(CircleShape),
                    contentDescription = null
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    modifier = Modifier
                        .align(Alignment.CenterVertically),
                    text = profileName,
                    fontSize = 14.sp,
                    color = SportFinderLightColorScheme.primary
                )
            }
            Spacer(modifier = Modifier.height(24.dp))

            TabRow(selectedTabIndex = tabIndex) {
                tabs.forEachIndexed { index, tabNavItem ->
                    Tab(
                        selected = tabIndex == index,
                        onClick = {
                            viewModel.switchTab(index)
                            tabNavItem.onTabClick()
                        },
                        selectedContentColor = SportFinderLightColorScheme.primary,
                        unselectedContentColor = SportFinderLightColorScheme.secondary,
                        icon = {
                            Icon(
                                painter = tabNavItem.icon,
                                contentDescription = null
                            )
                        }
                    )
                }
            }

            // TODO: Добавить отображение списков
        }
    }
}

@Preview
@Composable
fun ProfileScreenPreview() {
    ProfileScreen()
}