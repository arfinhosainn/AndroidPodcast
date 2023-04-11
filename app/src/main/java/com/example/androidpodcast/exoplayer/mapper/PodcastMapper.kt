package com.example.androidpodcast.exoplayer.mapper

import android.net.Uri
import androidx.media3.common.MediaItem
import com.example.androidpodcast.domain.model.EpisodeSong
import com.example.androidpodcast.exoplayer.common.Constants
import com.example.androidpodcast.exoplayer.common.Constants.ALBUM_ID
import com.example.androidpodcast.exoplayer.common.Constants.ARTIST_ID
import com.example.androidpodcast.exoplayer.util.buildPlayableMediaItem

internal fun EpisodeSong.asMediaItem() = buildPlayableMediaItem(
    mediaId = episode_id.toString(),
    artistId = author_id,
    albumId = show_id,
    mediaUri = Uri.parse(playback_url),
    artworkUri = Uri.parse(image_original_url),
    title = title,
    artist = ""
)

fun MediaItem?.asPodcast() = EpisodeSong(
    author_id = this?.mediaId?.toInt() ?: Constants.DEFAULT_MEDIA_ID,
    episode_id = this?.mediaMetadata?.extras?.getInt(ARTIST_ID) ?: -1,
    show_id = this?.mediaMetadata?.extras?.getInt(ALBUM_ID) ?: -1,
    playback_url = this?.requestMetadata?.mediaUri.toString().orEmpty(),
    image_original_url = this?.mediaMetadata?.artworkUri.toString().orEmpty(),
    title = this?.mediaMetadata?.title.toString().orEmpty(),
    image_url = this?.mediaMetadata?.artist.toString(),
    published_at = this?.mediaMetadata?.albumTitle.toString()
)

