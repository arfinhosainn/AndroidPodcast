package com.example.androidpodcast.presentation.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.androidpodcast.R
import com.example.androidpodcast.domain.model.PodcastList
import com.example.androidpodcast.ui.theme.Elevation

@Composable
fun HomeContent(
    paddingValues: PaddingValues,
    homeState: HomeScreenState,
    onPodcastClick: (PodcastList) -> Unit

) {
    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize()) {
            LazyColumn {
                item {
                    NewPodcast(
                        paddingValues = paddingValues,
                        podcast = homeState,
                        onPodcastClick = onPodcastClick
                    )
                }
                item {
                    RecentPodcastHolder(homeState = homeState)
                }
            }
        }
        if (homeState.isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }
    }
}

@Composable
fun NewPodcast(
    paddingValues: PaddingValues,
    podcast: HomeScreenState,
    onPodcastClick: (PodcastList) -> Unit
) {
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
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .clip(shape = RoundedCornerShape(bottomStart = 40.dp)),
            contentAlignment = Alignment.Center
        ) {
            Image(
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop,
                painter = painterResource(id = R.drawable.blurimage),
                contentDescription = "background Image"
            )
            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(30.dp),
                contentPadding = PaddingValues(horizontal = 30.dp)
            ) {
                items(podcast.podcasts) { podcast ->
                    Surface(
                        modifier = Modifier
                            .clip(shape = Shapes().extraLarge)
                            .clickable {
                                onPodcastClick(podcast)
                            }
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
                                model = podcast.image_original_url,
                                contentDescription = "",
                                contentScale = ContentScale.Crop
                            )
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(Color.Black.copy(alpha = 0.5f))
                            )
                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(
                                        start = 33.dp,
                                        end = 24.dp,
                                        bottom = 25.dp
                                    ),
                                verticalArrangement = Arrangement.Center
                            ) {
                                Spacer(modifier = Modifier.height(8.dp))
                                NewPodcastFooter(
                                    publisher = podcast.last_episode_at.take(10),
                                    title = podcast.title
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun NewPodcastFooter(
    publisher: String,
    title: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Row() {
                Text(
                    text = title,
                    style = TextStyle(
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Medium
                    ),
                    maxLines = 4,
                    overflow = TextOverflow.Ellipsis
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            Row() {
                Icon(imageVector = Icons.Default.DateRange, contentDescription = "Date Icon")
                Text(
                    text = publisher,
                    style = TextStyle(
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Normal
                    )
                )
            }
        }
        Spacer(modifier = Modifier.width(10.dp))

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
                modifier = Modifier.size(20.dp)
            )
        }
    }
}

@Composable
fun RecentPodcastHolder(
    homeState: HomeScreenState
) {
    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(30.dp),
        contentPadding = PaddingValues(horizontal = 30.dp)
    ) {
        items(homeState.episodes) {
            RecentPodcastItem(
                title = it.title,
                duration = it.published_at,
                author = "John Doe",
                thumbnail = it.image_original_url
            )
        }
    }
}

@Composable
fun RecentPodcastItem(
    title: String,
    duration: String,
    author: String,
    thumbnail: String
) {
    Surface(
        modifier = Modifier
            .height(234.dp)
            .width(147.dp)
    ) {
        Column() {
            Card(
                modifier = Modifier
                    .height(136.dp)
                    .width(147.dp),
                shape = RoundedCornerShape(16.dp, bottomEnd = 0.dp)
            ) {
                AsyncImage(
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop,
                    model = thumbnail,
                    contentDescription = "Podcast Thumbnail"
                )
            }
            Text(
                text = title,
                style = TextStyle(
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium
                ),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = duration,
                style = TextStyle(
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Normal
                )
            )
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = author,
                    style = TextStyle(
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Normal
                    )
                )

                Box(
                    modifier = Modifier
                        .size(40.dp)
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
                        modifier = Modifier.size(10.dp)
                    )
                }
            }
        }
    }
}

// @Preview
// @Composable
// fun PreviewRecentPodcast() {
//    RecentPodcast(
//        title = "Miami isn't the best place to live",
//        duration = "24:53",
//        author = "John Smith"
//    )
// }
