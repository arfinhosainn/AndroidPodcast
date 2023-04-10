package com.example.androidpodcast.presentation.details

import android.net.Uri
import com.example.androidpodcast.domain.model.EpisodeSong
import com.example.androidpodcast.media.PlaybackState
import com.example.androidpodcast.util.MediaConstants.DEFAULT_ALBUM_ID
import com.example.androidpodcast.util.MediaConstants.DEFAULT_ARTIST_ID
import com.example.androidpodcast.util.MediaConstants.DEFAULT_DURATION_MS
import com.example.androidpodcast.util.MediaConstants.DEFAULT_MEDIA_ID

data class MusicState(
    val currentSong: EpisodeSong = EpisodeSong(
        episode_id = DEFAULT_MEDIA_ID,
        author_id = DEFAULT_ARTIST_ID,
        show_id = DEFAULT_ALBUM_ID,
        playback_url = Uri.EMPTY.toString(),
        image_url = Uri.EMPTY.toString(),
        title = "",
        image_original_url = "",
        published_at = ""
    ),
    val playbackState: PlaybackState = PlaybackState.IDLE,
    val playWhenReady: Boolean = false,
    val duration: Long = DEFAULT_DURATION_MS
)