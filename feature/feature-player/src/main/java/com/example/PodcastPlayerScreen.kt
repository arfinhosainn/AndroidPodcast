package com.example

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.items
import com.example.components.PlayerHeader
import com.example.components.PodcastContent
import com.example.components.PodcastListScreen
import com.example.mappers.Episode
import com.example.model.EpisodeSong

@Composable
fun PodcastPlayerScreen(
    playWhenReady: Boolean,
    trackImageUrl: String,
    onMediaButtonPlayClick: () -> Unit,
    onMediaButtonPauseClick: () -> Unit,
    onMediaButtonSkipNextClick: () -> Unit,
    currentPosition: Long,
    duration: Long,
    onSkipTo: (Float) -> Unit,
    onEpisodeSelected: (Episode) -> Unit,
    onDownLoadClick: (String) -> Unit,
    detailScreenState: DetailScreenState,
    title: String,
    episodes: LazyPagingItems<Episode>

) {
    val state = rememberLazyListState()

    LazyColumn(verticalArrangement = Arrangement.spacedBy((-17).dp), state = state) {

        item {
            PlayerHeader(
                trackImageUrl = trackImageUrl,
                playWhenReady = playWhenReady,
                play = onMediaButtonPlayClick,
                pause = onMediaButtonPauseClick,
                forward10 = onMediaButtonSkipNextClick,
                previous = {},
                title = title
            )
        }
        item {
            PodcastContent(
                currentPosition = currentPosition,
                duration = duration,
                onSkipTo = onSkipTo,
                episodeCount = detailScreenState.episode.size
            )
        }
        items(episodes) { episodes ->
            if (episodes != null) {
                PodcastListScreen(
                    onEpisodeSelected = onEpisodeSelected,
                    episodeSong = episodes,
                    onDownLoadClick = onDownLoadClick
                )

            }
        }
        item {
            if (episodes.loadState.append is LoadState.Loading) {
                CircularProgressIndicator()
            }
        }
    }
}
