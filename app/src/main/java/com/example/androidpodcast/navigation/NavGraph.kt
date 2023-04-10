package com.example.androidpodcast.navigation

import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.androidpodcast.presentation.details.PodcastDetailScreen
import com.example.androidpodcast.presentation.details.PodcastDetailsViewModel
import com.example.androidpodcast.presentation.home.HomeScreen
import com.example.androidpodcast.presentation.home.HomeViewModel

@Composable
fun SetupNavGraph(
    navController: NavHostController
) {
    NavHost(navController = navController, startDestination = Screen.HomeScreen.route) {
        homeRoute(navController)
        detailRoute()
    }
}

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

fun NavGraphBuilder.detailRoute() {
    composable(route = Screen.DetailScreen.route + "/{showId}") {
        val viewModel = hiltViewModel<PodcastDetailsViewModel>()
        val detailState by viewModel.detailState.collectAsStateWithLifecycle()

        PodcastDetailScreen()
    }
}
