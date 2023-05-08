package com.example.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.feature_player.R

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
                    ).clickable(onClick = {
                        pause()
                    })
                    .background(Color.Red, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    imageVector = ImageVector.vectorResource(id = R.drawable.pause),
                    contentDescription = "Play Icon",
                    modifier = Modifier.size(20.dp)
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
                    ).clickable(onClick = {
                        play()
                    })
                    .background(Color.Red, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    imageVector = ImageVector.vectorResource(id = R.drawable.play),
                    contentDescription = "Play Icon",
                    modifier = Modifier.size(20.dp)
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

 @Preview
 @Composable
 fun PreviewPlayerButton() {
    PlayerButtons(
        playWhenReady = true,
        play = { /*TODO*/ },
        pause = { /*TODO*/ },
        forward10 = { /*TODO*/ },

        previous = { /*TODO*/ }
    )
 }