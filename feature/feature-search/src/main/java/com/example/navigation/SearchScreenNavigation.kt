package com.example.navigation

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.SearchScreen
import com.example.SearchViewModel
import com.example.util.Screen

fun NavGraphBuilder.searchRoute() {
    composable(route = Screen.SearchScreen.route) {

        val viewModel = hiltViewModel<SearchViewModel>()
        val searchState by viewModel.searchState.collectAsStateWithLifecycle()

        SearchScreen(
            onTextChange = {
                viewModel.getSearchedEpisodes(it)
                Log.d("text", "searchRoute: $it")
            },
            searchState = searchState
        )
    }
}