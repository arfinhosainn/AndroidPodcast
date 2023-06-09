package com.example.util

sealed class Screen(val route: String) {
    object HomeScreen : Screen("coin_list_screen")
    object DetailScreen : Screen("coin_detail_screen")
    object SearchScreen : Screen("search_screen")
    object PlayerScreen : Screen("player_screen")
}