package com.example.util

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import androidx.core.graphics.drawable.toBitmap
import androidx.media3.common.C
import androidx.media3.common.Player
import coil.ImageLoader
import coil.request.ImageRequest
import com.example.common.Constants
import com.example.common.PlaybackState
import com.example.core_util.R
import java.text.SimpleDateFormat
import java.util.Locale

fun Int.asPlaybackState() = when (this) {
    Player.STATE_IDLE -> PlaybackState.IDLE
    Player.STATE_BUFFERING -> PlaybackState.BUFFERING
    Player.STATE_READY -> PlaybackState.READY
    Player.STATE_ENDED -> PlaybackState.ENDED
    else -> error(Constants.INVALID_PLAYBACK_STATE_ERROR_MESSAGE)
}


fun String.toFormattedDateString(): String {
    val inputDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
    val outputDateFormat = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
    val date = inputDateFormat.parse(this)
    return outputDateFormat.format(date!!)
}

fun Int.toFormattedDuration(): String {
    val seconds = (this / 1000)
    val hours = seconds / 3600
    val minutes = (seconds % 3600) / 60
    val secs = seconds % 60
    return String.format("%02d:%02d:%02d", hours, minutes, secs)
}


 fun Long.orDefaultTimestamp() = takeIf { it != C.TIME_UNSET }
    ?: Constants.DEFAULT_DURATION_MS

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