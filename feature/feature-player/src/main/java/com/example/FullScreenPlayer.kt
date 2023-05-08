package com.example

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.components.PlayerButtons
import com.example.components.PlayerTimeSlider
import com.example.components.PlayerTopBar

@Composable
fun FullScreenPlayer(
    trackImageUrl: String,
    modifier: Modifier = Modifier,
    playWhenReady: Boolean,
    play: () -> Unit,
    pause: () -> Unit,
    forward10: () -> Unit,
    previous: () -> Unit,
    currentPosition: Long,
    duration: Long,
    onSkipTo: (Float) -> Unit,
    onBackPressed: () -> Unit
) {

    Scaffold(topBar = {
        PlayerTopBar(onMenuClick = { /*TODO*/ },
            onBackPressed = onBackPressed)

    },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {


                Box(
                    modifier = Modifier
                        .size(220.dp)
                        .clip(shape = RoundedCornerShape(20.dp))
                ) {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(trackImageUrl)
                            .crossfade(true)
                            .build(),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = modifier
                            .fillMaxWidth()
                            .height(350.dp)
                            .aspectRatio(1f)
                            .clip(MaterialTheme.shapes.medium)
                    )
                }

                PlayerTimeSlider(
                    currentPosition = currentPosition,
                    duration = duration,
                    onSkipTo = onSkipTo
                )

                PlayerButtons(
                    playWhenReady = playWhenReady,
                    play = play,
                    pause = pause,
                    forward10 = forward10, previous = previous
                )
            }


        }
    )


}