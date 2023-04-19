package com.example.androidpodcast.presentation.player

import android.net.Uri

data class Song(
    val mediaId: Int,
    val artistId: Long,
    val albumId: Long,
    val mediaUri: Uri,
    val artworkUri: Uri,
    val title: String,
    val artist: String,
    val album: String
)