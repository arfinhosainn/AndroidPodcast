package com.example.androidpodcast.navigation

sealed class Screen(val route: String) {
    object HomeScreen : Screen("coin_list_screen")
    object DetailScreen : Screen("coin_detail_screen")
}