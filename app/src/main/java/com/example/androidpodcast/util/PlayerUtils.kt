package com.example.androidpodcast.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.media3.common.C
import androidx.media3.common.Player
import com.example.androidpodcast.R
import com.example.androidpodcast.media.PlaybackState
import com.example.androidpodcast.util.MediaConstants.DEFAULT_DURATION_MS
import com.example.androidpodcast.util.MediaConstants.INVALID_PLAYBACK_STATE_ERROR_MESSAGE
import java.util.*
import kotlin.time.Duration.Companion.milliseconds

internal fun Int.asPlaybackState() = when (this) {
    Player.STATE_IDLE -> PlaybackState.IDLE
    Player.STATE_BUFFERING -> PlaybackState.BUFFERING
    Player.STATE_READY -> PlaybackState.READY
    Player.STATE_ENDED -> PlaybackState.ENDED
    else -> error(INVALID_PLAYBACK_STATE_ERROR_MESSAGE)
}

@Composable
fun Long.asFormattedString() = milliseconds.toComponents { minutes, seconds, _ ->
    stringResource(
        id = R.string.skip_next,
        String.format(locale = Locale.US, format = "%02d", minutes),
        String.format(locale = Locale.US, format = "%02d", seconds)
    )
}

fun convertToProgress(count: Long, total: Long) =
    ((count * ProgressDivider) / total / ProgressDivider).takeIf(Float::isFinite) ?: ZeroProgress

private const val ProgressDivider = 100f
private const val ZeroProgress = 0f
internal fun Long.orDefaultTimestamp() = takeIf { it != C.TIME_UNSET } ?: DEFAULT_DURATION_MS