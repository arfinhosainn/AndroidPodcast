package com.example.navigation

import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.PodcastDetailsViewModel
import com.example.PodcastPlayerScreen
import com.example.downloader.PodcastDownloader
import com.example.util.Screen
import com.example.util.toFormattedDuration

fun NavGraphBuilder.detailRoute() {
    composable(route = Screen.DetailScreen.route + "/{showId}") {
        val viewModel = hiltViewModel<PodcastDetailsViewModel>()
        val currentSliderState by viewModel.currentPosition.collectAsStateWithLifecycle()
        val podcastState by viewModel.musicState.collectAsStateWithLifecycle()
        val detailsState by viewModel.detailState.collectAsStateWithLifecycle()
        val context = LocalContext.current
        val downloader = PodcastDownloader(context)

        PodcastPlayerScreen(
            playWhenReady = podcastState.playWhenReady,
            trackImageUrl = podcastState.currentPodcast.image_original_url,
            onMediaButtonPlayClick = {
                viewModel.resume()
            },
            onMediaButtonPauseClick = {
                viewModel.pause()
            },
            onMediaButtonSkipNextClick = viewModel::seekTo10Seconds,
            currentPosition = currentSliderState,
            duration = podcastState.duration,
            onSkipTo = viewModel::skipTo,
            onEpisodeSelected = {
                viewModel.playPodcast(listOf(it))
            },
            detailScreenState = detailsState,
            onDownLoadClick = {
                downloader.downloadPodcast(it)
            },
            title = podcastState.currentPodcast.title
        )
    }

}
