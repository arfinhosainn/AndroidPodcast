package com.example.androidpodcast.presentation.home

import com.example.androidpodcast.data.remote.mappers.Episode
import com.example.androidpodcast.domain.model.PodcastList

data class HomeScreenState(
    val isLoading: Boolean = true,
    val podcasts: List<PodcastList> = emptyList(),
    val episodes: List<Episode> = emptyList(),
    val podcastListError: String = "",
    val episodeListError: String = ""
)