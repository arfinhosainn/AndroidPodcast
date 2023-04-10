package com.example.androidpodcast.presentation.details

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.androidpodcast.presentation.details.components.PlayerMediaButtons
import com.example.androidpodcast.presentation.details.components.PlayerTimeSlider

@Composable
fun PodcastDetailScreen(
    viewModel: PodcastDetailsViewModel = hiltViewModel(),
    modifier: Modifier = Modifier
) {
    val musicState by viewModel.musicState.collectAsStateWithLifecycle()
    val currentPosition by viewModel.currentPosition.collectAsStateWithLifecycle()

    PlayerScreen(
        modifier = modifier,
        musicState = musicState,
        currentPosition = currentPosition,
        onSkipTo = viewModel::skipTo,
        onMediaButtonSkipPreviousClick = viewModel::skipPrevious,
        onMediaButtonPlayClick = viewModel::play,
        onMediaButtonPauseClick = viewModel::pause,
        onMediaButtonSkipNextClick = viewModel::skipNext
    )
}

@Composable
private fun PlayerScreen(
    musicState: MusicState,
    currentPosition: Long,
    onSkipTo: (Float) -> Unit,
    onMediaButtonSkipPreviousClick: () -> Unit,
    onMediaButtonPlayClick: () -> Unit,
    onMediaButtonPauseClick: () -> Unit,
    onMediaButtonSkipNextClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    PlayerTimeSlider(
        currentPosition = currentPosition,
        duration = musicState.duration,
        onSkipTo = onSkipTo
    )

    PlayerMediaButtons(
        playWhenReady = musicState.playWhenReady,
        onSkipPreviousClick = onMediaButtonSkipPreviousClick,
        onPlayClick = onMediaButtonPlayClick,
        onPauseClick = onMediaButtonPauseClick,
        onSkipNextClick = onMediaButtonSkipNextClick
    )
}
