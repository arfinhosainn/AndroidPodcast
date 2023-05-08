package com.example.navigation

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.PodcastDetailsViewModel
import com.example.SearchScreen
import com.example.SearchViewModel
import com.example.util.Screen

fun NavGraphBuilder.searchRoute(
    navController: NavController
) {
    composable(route = Screen.SearchScreen.route) {

        val viewModel = hiltViewModel<SearchViewModel>()
        val podcastViewModel = hiltViewModel<PodcastDetailsViewModel>()
        val searchState by viewModel.searchState.collectAsStateWithLifecycle()

        SearchScreen(
            onTextChange = {
                viewModel.getSearchedEpisodes(it)
                Log.d("text", "searchRoute: $it")
            },
            searchState = searchState,
            onPodcastClick = {
                navController.navigate(Screen.PlayerScreen.route)
                podcastViewModel.playPodcast(listOf(it))


            }
        )
    }
}