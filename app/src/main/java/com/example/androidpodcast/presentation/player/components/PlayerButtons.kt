package com.example.androidpodcast.presentation.player.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.example.androidpodcast.R

@Composable
fun PlayerButtons(
    modifier: Modifier = Modifier,
    playWhenReady: Boolean,
    play: () -> Unit,
    pause: () -> Unit,
    forward10: () -> Unit,
    previous: () -> Unit

) {
    Row(
        modifier = modifier.fillMaxWidth().padding(horizontal = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        IconButton(
            onClick = previous
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.back),
                modifier = Modifier.size(30.dp),
                contentDescription = stringResource(id = R.string.skip_previous),
                tint = Color.White
            )
        }
        if (playWhenReady) {
            Box(
                modifier = Modifier
                    .size(60.dp)
                    .shadow(
                        elevation = 10.dp,
                        shape = CircleShape,
                        clip = true,
                        ambientColor = Color.Red,
                        spotColor = Color.Red
                    )
                    .background(Color.Red, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    imageVector = ImageVector.vectorResource(id = R.drawable.pause),
                    contentDescription = "Play Icon",
                    modifier = Modifier.size(20.dp).clickable {
                        play()
                    }
                )
            }
        } else {
            Box(
                modifier = Modifier
                    .size(60.dp)
                    .shadow(
                        elevation = 10.dp,
                        shape = CircleShape,
                        clip = true,
                        ambientColor = Color.Red,
                        spotColor = Color.Red
                    )
                    .background(Color.Red, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    imageVector = ImageVector.vectorResource(id = R.drawable.play),
                    contentDescription = "Play Icon",
                    modifier = Modifier.size(20.dp).clickable {
                        pause()
                    }
                )
            }
        }
        IconButton(
            onClick = forward10
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.forward),
                modifier = Modifier.size(30.dp),
                contentDescription = stringResource(id = R.string.skip_next),
                tint = Color.White
            )
        }
    }
}

// @Preview
// @Composable
// fun PreviewPlayerButton() {
//    PlayerButtons(
//        playWhenReady = true,
//        play = { /*TODO*/ },
//        pause = { /*TODO*/ },
//        replay10 = { /*TODO*/ },
//        forward10 = { /*TODO*/ },
//        next = { /*TODO*/ },
//        previous = { /*TODO*/ }
//    )
// }