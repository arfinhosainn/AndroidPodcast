package com.example.androidpodcast.presentation.details

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.SimpleExoPlayer
import androidx.media3.ui.PlayerView

@Composable
fun PodcastDetailScreen(
    viewModel: PodcastDetailsViewModel = hiltViewModel(),
    modifier: Modifier = Modifier
) {
    val episode by viewModel.detailState.collectAsState()

    LazyColumn {
        items(episode.episode) {
            PodcastEpisodePlayer(playbackUrl = it.playback_url)
        }
    }
}

@androidx.annotation.OptIn(androidx.media3.common.util.UnstableApi::class)
@Composable
fun PodcastEpisodePlayer(playbackUrl: String) {
    val contesxt = LocalContext.current

    val exoPlayer = remember {
        SimpleExoPlayer.Builder(contesxt).build().apply {
            setMediaItem(MediaItem.fromUri(playbackUrl))
            prepare()
            playWhenReady = true
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            exoPlayer.release()
        }
    }

    AndroidView(
        factory = { context ->
            PlayerView(context).apply {
                player = exoPlayer
            }
        },
        modifier = Modifier.fillMaxWidth()
    )
}
