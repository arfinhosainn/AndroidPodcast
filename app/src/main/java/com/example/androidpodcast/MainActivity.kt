package com.example.androidpodcast

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.androidpodcast.presentation.home.HomeScreen
import com.example.androidpodcast.presentation.home.HomeViewModel
import com.example.androidpodcast.ui.theme.AndroidPodcastTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AndroidPodcastTheme(dynamicColor = false) {
                val viewModel = hiltViewModel<HomeViewModel>()

                val list by viewModel.curatePodcastList.collectAsState()

                HomeScreen(
                    onMenuClick = { /*TODO*/ },
                    onSearchBarClicked = { /*TODO*/ },
                    drawerState = DrawerState(DrawerValue.Closed),
                    onSignOutClicked = { /*TODO*/ },
                    onDeleteAllClicked = { /*TODO*/ },
                    homeScreenState = list
                )
            }
        }
    }
}
