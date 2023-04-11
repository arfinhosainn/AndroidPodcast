package com.example.androidpodcast.media

import androidx.media3.common.MediaItem
import com.example.androidpodcast.domain.model.EpisodeSong
import com.example.androidpodcast.media.MediaConstants.DEFAULT_ALBUM_ID
import com.example.androidpodcast.media.MediaConstants.DEFAULT_MEDIA_ID

fun MediaItem.asPodcast() = EpisodeSong(
    episode_id = this?.mediaId?.toInt() ?: DEFAULT_MEDIA_ID,
    author_id = this?.mediaMetadata?.extras?.getInt(ARTIST_ID) ?: DEFAULT_ALBUM_ID,
    albumId

)

object MediaConstants {
    const val DEFAULT_MEDIA_ID = -1
    const val DEFAULT_ARTIST_ID = 0L
    const val DEFAULT_ALBUM_ID = 0
    const val DEFAULT_INDEX = 0
    const val DEFAULT_POSITION_MS = 0L
    const val DEFAULT_DURATION_MS = 0L
}


internal const val ARTIST_ID = "artist_id"
internal const val ALBUM_ID = "album_id"