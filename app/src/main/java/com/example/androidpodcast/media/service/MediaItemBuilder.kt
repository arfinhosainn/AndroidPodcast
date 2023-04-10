package com.example.androidpodcast.media.service

import android.net.Uri
import androidx.core.os.bundleOf
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
fun buildPlayableMediaItem(
    mediaId: String,
    artistId: Long,
    albumId: Long,
    mediaUri: Uri,
    artworkUri: Uri,
    title: String,
    artist: String
) = MediaItem.Builder()
    .setMediaId(mediaId)
    .setRequestMetadata(
        MediaItem.RequestMetadata.Builder()
            .setMediaUri(mediaUri)
            .build()
    )
    .setMediaMetadata(
        MediaMetadata.Builder()
            .setArtworkUri(artworkUri)
            .setTitle(title)
            .setArtist(artist)
            .setFolderType(MediaMetadata.FOLDER_TYPE_NONE)
            .setIsPlayable(true)
            .setExtras(bundleOf(ARTIST_ID to artistId, ALBUM_ID to albumId))
            .build()
    )
    .build()

internal const val ARTIST_ID = "artist_id"
internal const val ALBUM_ID = "album_id"