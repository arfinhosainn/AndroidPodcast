package com.example

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.components.PlayerButtons

@Composable
fun FullScreenPlayer(
    trackImageUrl: String,
    modifier: Modifier = Modifier,
    playWhenReady: Boolean,
    play: () -> Unit,
    pause: () -> Unit,
    forward10: () -> Unit,
    previous: () -> Unit,
    title: String
) {
    Column(modifier = Modifier.fillMaxSize()) {
        PlayerButtons(
            playWhenReady = playWhenReady,
            play = play,
            pause = pause,
            forward10 = forward10, previous = previous)
    }

}