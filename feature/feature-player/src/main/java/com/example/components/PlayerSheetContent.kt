package com.example.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.feature_player.R
import com.example.model.EpisodeSong
import com.example.ui.theme.lightBlue

@Composable
fun PodcastContent(
    currentPosition: Long,
    duration: Long,
    onSkipTo: (Float) -> Unit,
    episodeCount: Int

) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .clip(shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
            .background(MaterialTheme.colorScheme.onSurface)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 33.dp, end = 33.dp, top = 45.dp)
        ) {
            PlayerTimeSlider(
                currentPosition = currentPosition,
                duration = duration,
                onSkipTo = onSkipTo
            )
            Spacer(modifier = Modifier.height(28.dp))
            InfoSection()
            Spacer(modifier = Modifier.height(18.dp))
            Divider(color = Color.Gray)
            Spacer(modifier = Modifier.height(28.dp))
            PodcastDetails()
            Spacer(modifier = Modifier.height(40.dp))
            Text(
                text = "Episodes ($episodeCount)",
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(start = 8.dp, end = 16.dp)
            )
            Spacer(modifier = Modifier.height(18.dp))
        }
    }
}

@Composable
fun PodcastListScreen(
    onEpisodeSelected: (EpisodeSong) -> Unit,
    episodeSong: EpisodeSong
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.onSurface)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 33.dp, end = 33.dp, bottom = 15.dp, top = 18.dp)
        ) {
            PodcastEpisodeList(onClick = onEpisodeSelected, episodeSong = episodeSong)
        }
    }
}

@Composable
fun PodcastEpisodeList(
    onClick: (EpisodeSong) -> Unit,
    episodeSong: EpisodeSong
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .padding(vertical = 6.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(MaterialTheme.colorScheme.background),
        tonalElevation = 4.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .shadow(
                            elevation = 10.dp,
                            shape = CircleShape,
                            clip = true,
                            ambientColor = lightBlue,
                            spotColor = lightBlue
                        )
                        .clickable(onClick = {
                            onClick(episodeSong)
                        })
                        .background(lightBlue, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        imageVector = ImageVector.vectorResource(id = R.drawable.play),
                        contentDescription = "Play Icon",
                        modifier = Modifier.size(10.dp)
                    )
                }
                Spacer(modifier = Modifier.width(16.dp))
                Column {
                    Text(
                        text = episodeSong.published_at,
                        style = TextStyle(
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        ),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = "Second Text",
                        style = TextStyle(
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Normal
                        ),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
            Row(
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = episodeSong.title,
                        style = TextStyle(
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Normal
                        ),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = episodeSong.title,
                        style = TextStyle(
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        ),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                Spacer(modifier = Modifier.width(16.dp))
                Image(
                    modifier = Modifier.size(30.dp),
                    imageVector = ImageVector.vectorResource(id = R.drawable.download),
                    contentDescription = ""
                )
            }
        }
    }
}

val episodeNumber = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)

// @Preview(uiMode = UI_MODE_NIGHT_YES)
// @Composable
// fun PreviewPodcastEpisodeList() {
//    PodcastEpisodeList(
//        episodeDate = "23 May 2019",
//        episodeDuration = "10:15:00",
//        downloadSize = "23mb"
//    )
// }

// @Preview()
// @Composable
// fun PreviewPlayerSheetContent() {
//    PodcastContent(currentPosition = 200L, duration = 343L, onSkipTo = {})
// }

@Preview
@Composable
fun PreviewIcon() {
    InfoSection()
}

@Composable
fun InfoSection() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.like),
                contentDescription = "Icon",
                modifier = Modifier.size(32.dp)
            )
            Text(
                text = "37 501",
                modifier = Modifier.padding(start = 16.dp)
            )
        }
        Text(
            text = "24:15:05",
            modifier = Modifier.weight(1f),
            textAlign = TextAlign.Center
        )
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "37 501",
                modifier = Modifier.padding(end = 16.dp)
            )
            Image(
                painter = painterResource(id = R.drawable.unlike),
                contentDescription = "Icon",
                modifier = Modifier.size(32.dp)
            )
        }
    }
}

@Composable
fun PodcastDetails() {
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painterResource(id = R.drawable.wave),
                contentDescription = "Email icon",
                tint = Color.Unspecified
            )
            Text(
                text = "Episode 1",
                color = Color.White,
                fontSize = 12.sp,
                fontWeight = FontWeight.Normal,
                modifier = Modifier.padding(start = 8.dp, end = 16.dp)
            )
            Icon(
                painterResource(id = R.drawable.download),
                contentDescription = "Email icon",
                tint = Color.Unspecified
            )
            Text(
                text = "50mb",
                color = Color.White,
                fontSize = 12.sp,
                modifier = Modifier.padding(start = 8.dp, end = 16.dp)
            )
            Row(modifier = Modifier.weight(1f), horizontalArrangement = Arrangement.End) {
                Icon(
                    imageVector = Icons.Default.MoreVert,
                    contentDescription = "Settings icon",
                    tint = Color.White,
                    modifier = Modifier
                )
            }
        }
        Spacer(modifier = Modifier.height(41.dp))
        Text(
            text = "The Big Oxmox advised her not to do so," +
                " because there were thousands of bad Commas," +
                " wild Question Marks and devious Semikoli," +
                " but the Little Blind Text didn’t listen. ",
            style = TextStyle(
                fontSize = 13.sp,
                fontWeight = FontWeight.Normal,
                lineHeight = 20.sp
            )
        )
    }
}

@Preview
@Composable
fun PreviewPodcastDetails() {
    PodcastDetails()
}