package com.example.androidpodcast.exoplayer

import android.content.Context
import android.os.Bundle
import androidx.annotation.DrawableRes
import androidx.media3.common.Player
import androidx.media3.session.CommandButton
import androidx.media3.session.MediaSession
import androidx.media3.session.SessionCommand
import com.example.androidpodcast.R
import com.example.androidpodcast.exoplayer.common.Constants.REPEAT
import com.example.androidpodcast.exoplayer.common.Constants.REPEAT_ONE
import com.example.androidpodcast.exoplayer.common.Constants.REPEAT_SHUFFLE
import com.example.androidpodcast.exoplayer.common.Constants.SHUFFLE
import com.example.androidpodcast.exoplayer.common.Constants.UNHANDLED_STATE_ERROR_MESSAGE
import com.example.androidpodcast.exoplayer.common.Constants.UNKNOWN_CUSTOM_ACTION_ERROR_MESSAGE
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import javax.inject.Inject

class PodcastActionHandler @Inject constructor(
    @Dispatcher(PodcastDispatcher.MAIN) mainDispatcher: CoroutineDispatcher,
    @ApplicationContext private val context: Context
) {
    private val coroutineScope = CoroutineScope(mainDispatcher + SupervisorJob())

    private val customLayoutMap = mutableMapOf<String, CommandButton>()
    val customLayout: List<CommandButton> get() = customLayoutMap.values.toList()
    val customCommands = getAvailableCustomCommands()

    init {
        loadCustomLayout()
    }

    fun onCustomCommand(mediaSession: MediaSession, customCommand: SessionCommand) =
        when (customCommand.customAction) {
            REPEAT, REPEAT_ONE, SHUFFLE -> {
                handleRepeatShuffleCommand(
                    action = customCommand.customAction,
                    player = mediaSession.player
                )
            }
            else -> error("$UNKNOWN_CUSTOM_ACTION_ERROR_MESSAGE ${customCommand.customAction}")
        }

    fun cancelCoroutineScope() = coroutineScope.cancel()

    private fun handleRepeatShuffleCommand(action: String, player: Player) = when (action) {
        REPEAT -> {
            player.repeatMode = Player.REPEAT_MODE_ONE
            setRepeatShuffleCommand(REPEAT_ONE)
        }
        REPEAT_ONE -> {
            player.repeatMode = Player.REPEAT_MODE_ALL
            player.shuffleModeEnabled = true
            setRepeatShuffleCommand(SHUFFLE)
        }
        SHUFFLE -> {
            player.shuffleModeEnabled = false
            player.repeatMode = Player.REPEAT_MODE_ALL
            setRepeatShuffleCommand(REPEAT)
        }
        else -> error(UNHANDLED_STATE_ERROR_MESSAGE)
    }

    private fun setRepeatShuffleCommand(action: String) =
        customLayoutMap.set(REPEAT_SHUFFLE, customCommands.getValue(action))

    private fun loadCustomLayout() = customLayoutMap.run {
        this[REPEAT_SHUFFLE] = customCommands.getValue(REPEAT)
    }

    private fun getAvailableCustomCommands() = mapOf(
        REPEAT to buildCustomCommand(
            action = REPEAT,
            displayName = context.getString(R.string.repeat),
            iconResource = R.drawable.repeat
        ),
        REPEAT_ONE to buildCustomCommand(
            action = REPEAT_ONE,
            displayName = context.getString(R.string.repeat_one),
            iconResource = R.drawable.repeat_one
        ),
        SHUFFLE to buildCustomCommand(
            action = SHUFFLE,
            displayName = context.getString(R.string.shuffle),
            iconResource = R.drawable.shuffle
        )
    )
}

private fun buildCustomCommand(
    action: String,
    displayName: String,
    @DrawableRes iconResource: Int
) = CommandButton.Builder()
    .setSessionCommand(SessionCommand(action, Bundle.EMPTY))
    .setDisplayName(displayName)
    .setIconResId(iconResource)
    .build()


