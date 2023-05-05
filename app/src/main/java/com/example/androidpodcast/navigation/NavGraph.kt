package com.example.androidpodcast.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.example.navigation.detailRoute
import com.example.navigation.homeRoute
import com.example.navigation.playerRoute
import com.example.navigation.searchRoute
import com.example.util.Screen

@Composable
fun SetupNavGraph(
    navController: NavHostController
) {
    NavHost(navController = navController, startDestination = Screen.HomeScreen.route) {
        homeRoute(navController)
        detailRoute()
        searchRoute(navController)
        playerRoute(onBackPressed = {
            navController.navigate(Screen.SearchScreen.route)
        })
    }
}