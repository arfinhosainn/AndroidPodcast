package com.example.exoplayer

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import androidx.core.app.NotificationCompat
import androidx.core.content.getSystemService
import androidx.media3.common.util.UnstableApi
import androidx.media3.session.CommandButton
import androidx.media3.session.MediaNotification
import androidx.media3.session.MediaSession
import androidx.media3.session.MediaStyleNotificationHelper.MediaStyle
import com.example.common.Constants.MusicNotificationChannelId
import com.example.common.Constants.MusicNotificationId
import com.example.core_media.R
import com.example.di.Dispatcher
import com.example.di.PodcastDispatcher
import com.example.util.asArtworkBitmap
import com.google.common.collect.ImmutableList
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.*
import javax.inject.Inject

@UnstableApi class PodcastNotificationProvider @Inject constructor(
    @Dispatcher(PodcastDispatcher.MAIN) mainDispatcher: CoroutineDispatcher,
    @ApplicationContext private val context: Context,
    @Dispatcher(PodcastDispatcher.IO) private val ioDispatcher: CoroutineDispatcher
) : MediaNotification.Provider, Parcelable {
    private val notificationManager = checkNotNull(context.getSystemService<NotificationManager>())
    private val coroutineScope = CoroutineScope(mainDispatcher + SupervisorJob())

    constructor(parcel: Parcel) : this(
        TODO("mainDispatcher"),
        TODO("context"),
        TODO("ioDispatcher")
    ) {
    }

    override fun createNotification(
        session: MediaSession,
        customLayout: ImmutableList<CommandButton>,
        actionFactory: MediaNotification.ActionFactory,
        onNotificationChangedCallback: MediaNotification.Provider.Callback
    ): MediaNotification {
        ensureNotificationChannel()

        val player = session.player
        val metadata = player.mediaMetadata

        val builder = NotificationCompat.Builder(context, MusicNotificationChannelId)
            .setContentTitle(metadata.title)
            .setContentText(metadata.artist)
            .setSmallIcon(R.drawable.ic_play)
            .setStyle(MediaStyle(session))

        getNotificationActions(
            mediaSession = session,
            customLayout = customLayout,
            actionFactory = actionFactory,
            playWhenReady = player.playWhenReady
        ).forEach(builder::addAction)

        setupArtwork(
            uri = metadata.artworkUri,
            setLargeIcon = builder::setLargeIcon,
            updateNotification = {
                val notification = MediaNotification(MusicNotificationId, builder.build())
                onNotificationChangedCallback.onNotificationChanged(notification)
            }
        )

        return MediaNotification(MusicNotificationId, builder.build())
    }

    override fun handleCustomCommand(session: MediaSession, action: String, extras: Bundle) = false

    fun cancelCoroutineScope() = coroutineScope.cancel()

    private fun ensureNotificationChannel() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O ||
            notificationManager.getNotificationChannel(MusicNotificationChannelId) != null
        ) {
            return
        }

        val notificationChannel = NotificationChannel(
            MusicNotificationChannelId,
            context.getString(R.string.music_notification_channel_name),
            NotificationManager.IMPORTANCE_LOW
        )
        notificationManager.createNotificationChannel(notificationChannel)
    }

    private fun getNotificationActions(
        mediaSession: MediaSession,
        customLayout: ImmutableList<CommandButton>,
        actionFactory: MediaNotification.ActionFactory,
        playWhenReady: Boolean
    ) = listOf(
        PodcastActions.getRepeatShuffleAction(mediaSession, customLayout, actionFactory),
        PodcastActions.getSkipPreviousAction(context, mediaSession, actionFactory),
        PodcastActions.getPlayPauseAction(context, mediaSession, actionFactory, playWhenReady),
        PodcastActions.getSkipNextAction(context, mediaSession, actionFactory)
    )

    private fun setupArtwork(
        uri: Uri?,
        setLargeIcon: (Bitmap?) -> Unit,
        updateNotification: () -> Unit
    ) = coroutineScope.launch {
        val bitmap = loadArtworkBitmap(uri)
        setLargeIcon(bitmap)
        updateNotification()
    }

    private suspend fun loadArtworkBitmap(uri: Uri?) =
        withContext(ioDispatcher) { uri?.asArtworkBitmap(context) }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<PodcastNotificationProvider> {
        override fun createFromParcel(parcel: Parcel): PodcastNotificationProvider {
            return PodcastNotificationProvider(parcel)
        }

        override fun newArray(size: Int): Array<PodcastNotificationProvider?> {
            return arrayOfNulls(size)
        }
    }
}
