package com.example

import com.example.mappers.Episode
import com.example.model.PodcastList

data class HomeScreenState(
    val isLoading: Boolean = true,
    val podcasts: List<PodcastList> = emptyList(),
    val episodes: List<Episode> = emptyList(),
    val podcastListError: String = "",
    val episodeListError: String = ""
)