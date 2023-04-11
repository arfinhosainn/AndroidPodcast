package com.example.androidpodcast.exoplayer.util

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import androidx.core.graphics.drawable.toBitmap
import androidx.media3.common.C
import androidx.media3.common.Player
import coil.ImageLoader
import coil.request.ImageRequest
import com.example.androidpodcast.R
import com.example.androidpodcast.exoplayer.common.Constants
import com.example.androidpodcast.exoplayer.common.PlaybackState

fun Int.asPlaybackState() = when (this) {
    Player.STATE_IDLE -> PlaybackState.IDLE
    Player.STATE_BUFFERING -> PlaybackState.BUFFERING
    Player.STATE_READY -> PlaybackState.READY
    Player.STATE_ENDED -> PlaybackState.ENDED
    else -> error(Constants.INVALID_PLAYBACK_STATE_ERROR_MESSAGE)
}

internal fun Long.orDefaultTimestamp() = takeIf { it != C.TIME_UNSET } ?: Constants.DEFAULT_DURATION_MS

suspend fun Uri.asArtworkBitmap(context: Context): Bitmap? {
    val loader = ImageLoader(context)
    val request = ImageRequest.Builder(context)
        .data(this)
        .placeholder(R.drawable.music_cover)
        .error(R.drawable.music_cover)
        .build()

    val drawable = loader.execute(request).drawable
    return drawable?.toBitmap()
}