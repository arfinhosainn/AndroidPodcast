package com.example.androidpodcast.presentation.details

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun PodcastDetailScreen(
    viewModel: PodcastDetailsViewModel = hiltViewModel(),
    modifier: Modifier = Modifier
) {
    val episode by viewModel.detailState.collectAsState()

    Column {
        if (episode.isLoading) {
            CircularProgressIndicator()
        } else {
            val episode = episode.episode.firstOrNull()
            if (episode != null) {
                Text(text = episode.title)
                Button(onClick = {
                    viewModel.fkd()
                }) {
                    Text(text = "Play Episode")
                }
                Button(onClick = { viewModel.pause() }) {
                    Text(text = "Pause")
                }
                Button(onClick = { viewModel.skipNext() }) {
                    Text(text = "Skip")
                }
                Button(onClick = { viewModel.play() }) {
                    Text(text = "Play")
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))

        val currentPosition by viewModel.currentPosition.collectAsState(0L)
        Text(text = "Current position: ${currentPosition / 1000}s")
        episode.episode.forEach {
            Text(text = it.title)
        }
    }
}
