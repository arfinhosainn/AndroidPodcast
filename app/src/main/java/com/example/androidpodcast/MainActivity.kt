package com.example.androidpodcast

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import com.example.androidpodcast.presentation.home.HomeScreen
import com.example.androidpodcast.ui.theme.AndroidPodcastTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AndroidPodcastTheme(dynamicColor = false) {
                HomeScreen(
                    onMenuClick = { /*TODO*/ },
                    onSearchBarClicked = { /*TODO*/ },
                    drawerState = DrawerState(DrawerValue.Closed),
                    onSignOutClicked = { /*TODO*/ }) {

                }

            }
        }
    }
}
