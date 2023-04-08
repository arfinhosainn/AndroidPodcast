package com.example.androidpodcast.presentation.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.androidpodcast.R
import com.example.androidpodcast.ui.theme.Elevation

@Composable
fun HomeContent() {
}

@Composable
fun NewPodcast(paddingValues: PaddingValues, podcast: HomeScreenState) {
    var componentHeight by remember {
        mutableStateOf(0.dp)
    }

    val localDensity = LocalDensity.current

    Row(
        modifier = Modifier
            .clickable(
                indication = null,
                interactionSource = remember {
                    MutableInteractionSource()
                }
            ) {
            }
            .padding(top = paddingValues.calculateTopPadding())
    ) {
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(podcast.podcasts) { podcast ->
                Surface(
                    modifier = Modifier
                        .clip(shape = Shapes().medium)
                        .width(309.dp)
                        .height(192.dp)
                        .onGloballyPositioned {
                            componentHeight = with(localDensity) { it.size.height.toDp() }
                        },
                    tonalElevation = Elevation.Level2
                ) {
                    Box(modifier = Modifier.fillMaxSize()) {
                        AsyncImage(
                            modifier = Modifier.fillMaxSize(),
                            model = podcast.thumbnail,
                            contentDescription = "",
                            contentScale = ContentScale.Crop // crop the image to fit the box
                        )
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(Color.Black.copy(alpha = 0.3f)) // add a black overlay with 60% opacity
                        )
                        Column(
                            modifier = Modifier.fillMaxWidth().padding(
                                start = 33.dp,
                                end = 24.dp,
                                top = 32.dp,
                                bottom = 25.dp
                            ),
                            verticalArrangement = Arrangement.Center
                        ) {
                            NewPodcastHeader(title = podcast.title)
                            NewPodcastFooter(
                                publisher = podcast.publisher,
                                duration = 433,
                                totalEpisode = 353454
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun NewPodcastHeader(
    title: String
) {
    Row(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = title,
            style = TextStyle(
                fontSize = 24.sp,
                fontWeight = FontWeight.Medium
            )
        )
    }
}

@Composable
fun NewPodcastFooter(
    publisher: String,
    duration: Int,
    totalEpisode: Int
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Row {
                Icon(imageVector = Icons.Default.PlayArrow, contentDescription = "Play Icon")
                Text(text = totalEpisode.toString())
                Spacer(modifier = Modifier.width(20.dp))
                Icon(imageVector = Icons.Default.Delete, contentDescription = "Play Icon")
                Text(text = duration.toString())
            }
            Text(text = publisher)
        }

        Box(
            modifier = Modifier
                .size(60.dp).shadow(
                    elevation = 35.dp,
                    ambientColor = Color.Red,
                    spotColor = Color.Red,
                    shape = CircleShape
                )
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
}