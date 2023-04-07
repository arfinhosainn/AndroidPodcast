package com.example.androidpodcast.presentation.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.androidpodcast.R
import com.example.androidpodcast.ui.theme.Elevation

@Composable
fun HomeContent() {


}


@Composable
fun NewPodcast(paddingValues: PaddingValues) {

    var componentHeight by remember {
        mutableStateOf(0.dp)
    }

    val localDensity = LocalDensity.current

    Row(modifier = Modifier
        .clickable(
            indication = null,
            interactionSource = remember {
                MutableInteractionSource()
            }) {

        }
        .padding(top = paddingValues.calculateTopPadding())) {
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(10) {
                Surface(
                    modifier = Modifier
                        .clip(shape = Shapes().medium)
                        .width(309.dp)
                        .height(180.dp)
                        .onGloballyPositioned {
                            componentHeight = with(localDensity) { it.size.height.toDp() }
                        }, tonalElevation = Elevation.Level2
                ) {
                    Box(modifier = Modifier.fillMaxSize()) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_launcher_background),
                            contentDescription = "",
                            contentScale = ContentScale.Crop // crop the image to fit the box
                        )
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(Color.Black.copy(alpha = 0.6f)) // add a black overlay with 60% opacity
                        )
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            verticalArrangement = Arrangement.Center
                        ) {
                            NewPodcastHeader(title = "Arfin Hosain")
                            NewPodcastFooter(
                                publisher = "Arinf hosani ",
                                duration = 4343,
                                totalEpisode = 3252
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
            text = title, style = TextStyle(
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
                Text(text = totalEpisode.toString())
                Text(text = duration.toString())
            }
            Text(text = publisher)
        }
        Button(onClick = { /*TODO*/ }, shape = CircleShape) {
            Icon(imageVector = Icons.Default.PlayArrow, contentDescription = "Play Icon")
        }
    }
}