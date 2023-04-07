package com.example.androidpodcast.presentation.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.example.androidpodcast.R

@Composable
fun HomeScreen(
    onMenuClick: () -> Unit,
    onSearchBarClicked: () -> Unit,
    drawerState: DrawerState,
    onSignOutClicked: () -> Unit,
    onDeleteAllClicked: () -> Unit
) {
    var padding by remember {
        mutableStateOf(PaddingValues())
    }

    NavigationDrawer(
        drawerState = drawerState,
        onSignOutClicked = onSignOutClicked,
        onDeleteAllClicked = onDeleteAllClicked
    ) {
        Scaffold(topBar = {
            CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
                HomeTopBar(
                    onMenuClick = onMenuClick,
                    onSearchBarClicked = onSearchBarClicked
                )
            }

        }, content = {
            CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
                padding = it
                NewPodcast(paddingValues = it)
            }
        }
        )
    }
}

@Composable
internal fun NavigationDrawer(
    drawerState: DrawerState,
    onSignOutClicked: () -> Unit,
    onDeleteAllClicked: () -> Unit,
    content: @Composable () -> Unit
) {
    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        ModalNavigationDrawer(
            drawerState = drawerState,
            drawerContent = {
                CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
                    ModalDrawerSheet(
                        content = {
                            Image(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(250.dp),
                                painter = painterResource(id = R.drawable.logo),
                                contentDescription = "Logo Image"
                            )
                            NavigationDrawerItem(
                                label = {
                                    Row(modifier = Modifier.padding(horizontal = 12.dp)) {
                                        Image(
                                            painter = painterResource(id = R.drawable.logo),
                                            contentDescription = "Google Logo",
                                        )
                                        Spacer(modifier = Modifier.width(12.dp))
                                        Text(
                                            text = "Sign Out",
                                            color = MaterialTheme.colorScheme.onSurface
                                        )
                                    }
                                },
                                selected = false,
                                onClick = onSignOutClicked
                            )
                            NavigationDrawerItem(
                                label = {
                                    Row(modifier = Modifier.padding(horizontal = 12.dp)) {
                                        Icon(
                                            imageVector = Icons.Default.Delete,
                                            contentDescription = "Delete All Icon",
                                            tint = MaterialTheme.colorScheme.onSurface
                                        )
                                        Spacer(modifier = Modifier.width(12.dp))
                                        Text(
                                            text = "Delete All Diaries",
                                            color = MaterialTheme.colorScheme.onSurface
                                        )
                                    }
                                },
                                selected = false,
                                onClick = onDeleteAllClicked
                            )
                        }
                    )

                }
            },
            content = content
        )

    }
}


@Preview
@Composable
fun PreviewHomeScreen() {
    HomeScreen(
        onMenuClick = { /*TODO*/ },
        onSearchBarClicked = { /*TODO*/ },
        drawerState = DrawerState(DrawerValue.Open),
        onSignOutClicked = { /*TODO*/ }) {

    }

}
