package com.example.androidpodcast.presentation.player

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.androidpodcast.downloader.PodcastDownloader
import com.example.androidpodcast.presentation.player.components.PlayerHeader
import com.example.androidpodcast.presentation.player.components.PlayerTimeSlider

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
    onSkipTo: (Float) -> Unit
) {
    val context = LocalContext.current

    val downloader = PodcastDownloader(context)

    BottomSheetScaffold(
        sheetContent = {
            PlayerTimeSlider(
                currentPosition = currentPosition,
                duration = duration,
                onSkipTo = onSkipTo
            )
        },
        sheetPeekHeight = 500.dp,
        sheetContainerColor = MaterialTheme.colorScheme.onSurface
    ) {
        Column {
            PlayerHeader(
                trackImageUrl = trackImageUrl,
                playWhenReady = playWhenReady,
                play = onMediaButtonPlayClick,
                pause = onMediaButtonPauseClick,
                forward10 = onMediaButtonSkipNextClick

            ) {
            }
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