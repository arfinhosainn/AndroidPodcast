package com.example.androidpodcast.navigation

import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.androidpodcast.presentation.home.HomeScreen
import com.example.androidpodcast.presentation.home.HomeViewModel
import com.example.androidpodcast.presentation.player.PodcastDetailsViewModel
import com.example.androidpodcast.presentation.player.PodcastPlayerScreen

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
        val currentSliderState by viewModel.currentPosition.collectAsStateWithLifecycle()
        val podcastState by viewModel.musicState.collectAsStateWithLifecycle()
        val detailsState by viewModel.detailState.collectAsStateWithLifecycle()
        val scope = rememberCoroutineScope()

        PodcastPlayerScreen(
            playWhenReady = podcastState.playWhenReady,
            trackImageUrl = podcastState.currentSong.image_original_url,
            onMediaButtonPlayClick = {
//                viewModel.playPodcast()
            },
            onMediaButtonPauseClick = {
                viewModel.resume()
            },
            onMediaButtonSkipNextClick = viewModel::skipNext,
            currentPosition = currentSliderState,
            duration = podcastState.duration,
            onSkipTo = viewModel::skipTo,
            onEpisodeSelected = {
                viewModel.playPodcast(listOf(it))
            },
            detailScreenState = detailsState
        )
    }
}
