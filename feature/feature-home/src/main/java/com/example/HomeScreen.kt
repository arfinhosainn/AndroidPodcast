package com.example

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.DrawerState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import androidx.navigation.NavController
import com.example.util.Screen

@Composable
fun HomeScreen(
    onMenuClick: () -> Unit,
    onSearchBarClicked: () -> Unit,
    drawerState: DrawerState,
    onSignOutClicked: () -> Unit,
    onDeleteAllClicked: () -> Unit,
    homeScreenState: HomeScreenState,
    navController: NavController
) {
    var padding by remember {
        mutableStateOf(PaddingValues())
    }

        Scaffold(
            topBar = {
                CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
                    HomeTopBar(
                        onMenuClick = onMenuClick,
                        onSearchBarClicked = {
                            navController.navigate(Screen.SearchScreen.route)
                        }
                    )
                }
            },
            content = {
                CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
                    padding = it
                    HomeContent(
                        paddingValues = padding,
                        homeState = homeScreenState,
                        onPodcastClick = {
                            navController.navigate(Screen.DetailScreen.route + "/${it.show_id}")
                        }

                    )
                }
            }
        )
    }