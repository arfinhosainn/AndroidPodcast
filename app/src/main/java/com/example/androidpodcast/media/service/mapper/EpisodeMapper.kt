package com.example.androidpodcast.media.service.mapper

import android.net.Uri
import androidx.media3.common.MediaItem
import com.example.androidpodcast.domain.model.EpisodeSong
import com.example.androidpodcast.media.service.ALBUM_ID
import com.example.androidpodcast.media.service.ARTIST_ID
import com.example.androidpodcast.media.service.buildPlayableMediaItem
import com.example.androidpodcast.util.MediaConstants.DEFAULT_ALBUM_ID
import com.example.androidpodcast.util.MediaConstants.DEFAULT_ARTIST_ID
import com.example.androidpodcast.util.MediaConstants.DEFAULT_MEDIA_ID

fun EpisodeSong.asMediaItem() = buildPlayableMediaItem(
    mediaId = episode_id.toString(),
    artistId = author_id.toLong(),
    albumId = show_id.toLong(),
    mediaUri = Uri.parse(playback_url),
    artworkUri = Uri.parse(image_url),
    title = title,
    artist = published_at
)

fun MediaItem?.asEpisodeSong() = EpisodeSong(
    episode_id = this?.mediaId?.toInt() ?: DEFAULT_MEDIA_ID.toInt(),
    author_id = this?.mediaMetadata?.extras?.getInt(ARTIST_ID) ?: DEFAULT_ARTIST_ID.toInt(),
    show_id = this?.mediaMetadata?.extras?.getInt(ALBUM_ID) ?: DEFAULT_ALBUM_ID.toInt(),
    playback_url = this?.requestMetadata?.mediaUri.toString().orEmpty(),
    image_url = this?.mediaMetadata?.artworkUri.toString().orEmpty(),
    title = this?.mediaMetadata?.title.toString().orEmpty(),
    image_original_url = "",
    published_at = ""
)
