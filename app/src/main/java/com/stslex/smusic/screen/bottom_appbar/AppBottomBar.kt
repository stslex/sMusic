package com.stslex.smusic.screen.bottom_appbar

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.stslex.core.navigation.NavigationScreen

@Composable
fun AppBottomBar(
    modifier: Modifier = Modifier,
    onBottomAppBarClick: (NavigationScreen) -> Unit,
    selectedItem: BottomAppbarItem?
) {
    BottomAppBar(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        BottomAppbarItem.values().forEach { appBarItem ->
            val isSelected = selectedItem == appBarItem
            NavigationBarItem(
                selected = isSelected,
                onClick = {
                    onBottomAppBarClick(appBarItem.navDestination)
                },
                icon = {
                    Icon(
                        imageVector = if (isSelected) {
                            appBarItem.selectedIcon
                        } else {
                            appBarItem.unselectedIcon
                        },
                        contentDescription = null
                    )
                },
                label = {
                    val titleRes =
                        appBarItem.navDestination.destination.titleRes ?: return@NavigationBarItem
                    Text(
                        text = stringResource(id = titleRes),
                    )
                },
                alwaysShowLabel = false
            )
        }
    }
}