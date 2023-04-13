package com.example.androidpodcast.presentation.player.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.androidpodcast.ui.theme.lightBlue

@Composable
fun PlayerHeader(
    trackImageUrl: String,
    modifier: Modifier = Modifier,
    playWhenReady: Boolean,
    play: () -> Unit,
    pause: () -> Unit,
    forward10: () -> Unit,
    previous: () -> Unit
) {
    Box {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(trackImageUrl)
                .crossfade(true)
                .build(),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = modifier
                .fillMaxWidth()
                .height(320.dp)
                .aspectRatio(1f)
                .clip(MaterialTheme.shapes.medium)
        )

        Box(
            modifier = modifier
                .height(320.dp)
                .fillMaxWidth()
                .background(
                    Color.Black.copy(alpha = 0.5f)
                )
        ) {
            Box(
                modifier = modifier
                    .height(320.dp)
                    .fillMaxWidth()
                    .background(
                        Brush.verticalGradient(
                            listOf(
                                Color.Black.copy(alpha = 0.5f),
                                lightBlue.copy(alpha = 0.3f)
                            )
                        )
                    )
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 30.dp),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "About flow and our motivations",
                            style = TextStyle(
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Medium,
                                color = Color.White
                            ),
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis,
                            textAlign = TextAlign.Center
                        )
                    }

                    Spacer(modifier = Modifier.height(26.dp))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 30.dp),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "John Doe & Amanda Smith",
                            style = TextStyle(
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Medium,
                                color = Color.White
                            ),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            textAlign = TextAlign.Center
                        )
                    }

                    Spacer(modifier = Modifier.height(26.dp))

                    PlayerButtons(
                        playWhenReady = playWhenReady,
                        play = play,
                        pause = pause,
                        forward10 = forward10,
                        previous = previous
                    )
                }
            }
        }
    }
}

// @Preview
// @Composable
// fun PreviewPayerHeader() {
//    PlayerHeader(
//        trackImageUrl =
//        "https://d3wo5wojvuv7l.cloudfront.net/images.spreaker.com/original/" +
//            "89b95da0b9cae6f703a0b2b03eda38ce.jpg",
//        playWhenReady = true,
//        play = { /*TODO*/ },
//        pause = { /*TODO*/ },
//        replay10 = { /*TODO*/ },
//        forward10 = { /*TODO*/ },
//        next = { /*TODO*/ },
//        previous = { /*TODO*/ }
//    )
// }