package com.example.androidpodcast.presentation.player.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.androidpodcast.R
import java.util.*
import kotlin.time.Duration.Companion.milliseconds

@Composable
internal fun Long.asFormattedString() = milliseconds.toComponents { minutes, seconds, _ ->
    stringResource(
        id = R.string.player_time_stamp_format,
        String.format(locale = Locale.US, format = "%02d", minutes),
        String.format(locale = Locale.US, format = "%02d", seconds)
    )
}

internal fun convertToProgress(count: Long, total: Long) =
    ((count * ProgressDivider) / total / ProgressDivider).takeIf(Float::isFinite) ?: ZeroProgress

internal fun convertToPosition(value: Float, total: Long) = (value * total).toLong()

private const val ProgressDivider = 100f
private const val ZeroProgress = 0f