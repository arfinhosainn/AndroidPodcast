package com.example.navigation

import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.HomeScreen
import com.example.HomeViewModel
import com.example.PodcastDetailsViewModel
import com.example.util.Screen

fun NavGraphBuilder.homeRoute(
    navController: NavController
) {
    composable(route = Screen.HomeScreen.route) {
        val viewModel = hiltViewModel<HomeViewModel>()

        val list by viewModel.homeState.collectAsStateWithLifecycle()
        HomeScreen(
            onMenuClick = { /*TODO*/ },
            onSearchBarClicked = { /*TODO*/ },
            drawerState = DrawerState(DrawerValue.Closed),
            onSignOutClicked = { /*TODO*/ },
            onDeleteAllClicked = { /*TODO*/ },
            homeScreenState = list,
            navController = navController

        )
    }
}
