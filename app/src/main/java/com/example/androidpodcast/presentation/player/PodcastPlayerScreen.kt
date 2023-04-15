package com.example.androidpodcast.presentation.player

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.androidpodcast.domain.model.EpisodeSong
import com.example.androidpodcast.downloader.PodcastDownloader
import com.example.androidpodcast.presentation.player.components.PlayerHeader
import com.example.androidpodcast.presentation.player.components.PodcastContent
import com.example.androidpodcast.presentation.player.components.PodcastListScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PodcastPlayerScreen(
    playWhenReady: Boolean,
    trackImageUrl: String,
    onMediaButtonPlayClick: () -> Unit,
    onMediaButtonPauseClick: () -> Unit,
    onMediaButtonSkipNextClick: () -> Unit,
    currentPosition: Long,
    duration: Long,
    onSkipTo: (Float) -> Unit,
    onEpisodeSelected: (EpisodeSong) -> Unit,
    detailScreenState: DetailScreenState

) {
    val context = LocalContext.current
    val statre = rememberLazyListState()

    val downloader = PodcastDownloader(context)

    LazyColumn(verticalArrangement = Arrangement.spacedBy((-20).dp), state = statre) {
        item {
            PlayerHeader(
                trackImageUrl = trackImageUrl,
                playWhenReady = playWhenReady,
                play = onMediaButtonPlayClick,
                pause = onMediaButtonPauseClick,
                forward10 = onMediaButtonSkipNextClick
            ) {
            }
        }
        item {
            PodcastContent(
                currentPosition = currentPosition,
                duration = duration,
                onSkipTo = onSkipTo
            )
        }
        items(detailScreenState.episode) {
            PodcastListScreen(
                onEpisodeSelected = onEpisodeSelected,
                episodeSong = it
            )
        }
    }
}

// @Preview
// @Composable
// fun PreviewPodcastPlayerScreen() {
//    PodcastPlayerScreen(
//        playWhenReady = true,
//        trackImageUrl = ""
//    )
// }