package com.example.androidpodcast

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.getValue
import androidx.navigation.compose.rememberNavController
import com.example.androidpodcast.navigation.SetupNavGraph
import com.example.androidpodcast.ui.theme.AndroidPodcastTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AndroidPodcastTheme(dynamicColor = false) {
                val navigation = rememberNavController()
                SetupNavGraph(navController = navigation)
            }
        }
    }
}
