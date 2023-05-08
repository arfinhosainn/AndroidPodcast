package com.example.common

import com.example.model.EpisodeSong

data class PodcastState(
    val currentPodcast: EpisodeSong = EpisodeSong(
        author_id = Constants.DEFAULT_MEDIA_ID,
        episode_id = -1,
        show_id = -1,
        playback_url = "",
        image_original_url = "",
        title = "",
        image_url = "",
        published_at = "",
        download_url = "",
        duration = 0
    ),
    val playbackState: PlaybackState = PlaybackState.IDLE,
    val playWhenReady: Boolean = false,
    val duration: Long = Constants.DEFAULT_DURATION_MS
)

enum class PlaybackState { IDLE, BUFFERING, READY, ENDED }