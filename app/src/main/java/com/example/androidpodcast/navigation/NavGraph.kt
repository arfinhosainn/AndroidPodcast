package com.example.androidpodcast.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.example.navigation.detailRoute
import com.example.navigation.homeRoute
import com.example.util.Screen

@Composable
fun SetupNavGraph(
    navController: NavHostController
) {
    NavHost(navController = navController, startDestination = Screen.HomeScreen.route) {
        homeRoute(navController)
        detailRoute()
    }
}