package com.example.androidpodcast.exoplayer.common

import com.example.androidpodcast.domain.model.EpisodeSong

data class PodcastState(
    val currentSong: EpisodeSong = EpisodeSong(
        author_id = Constants.DEFAULT_MEDIA_ID,
        episode_id = -1,
        show_id = -1,
        playback_url = "",
        image_original_url = "",
        title = "",
        image_url = "",
        published_at = "",
        download_url = ""
    ),
    val playbackState: PlaybackState = PlaybackState.IDLE,
    val playWhenReady: Boolean = false,
    val duration: Long = Constants.DEFAULT_DURATION_MS
)

enum class PlaybackState { IDLE, BUFFERING, READY, ENDED }