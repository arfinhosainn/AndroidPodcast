package com.example.androidpodcast.util

import android.content.Context
import androidx.core.graphics.drawable.IconCompat
import androidx.media3.session.MediaNotification
import androidx.media3.session.MediaSession
import com.example.androidpodcast.media.MusicAction

@androidx.annotation.OptIn(androidx.media3.common.util.UnstableApi::class)
fun MusicAction.asNotificationAction(
    context: Context,
    mediaSession: MediaSession,
    actionFactory: MediaNotification.ActionFactory
) = actionFactory.createMediaAction(
    mediaSession,
    IconCompat.createWithResource(context, iconResource),
    context.getString(titleResource),
    command
)