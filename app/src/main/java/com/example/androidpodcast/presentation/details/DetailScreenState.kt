package com.example.androidpodcast.presentation.details

import com.example.androidpodcast.domain.model.EpisodeSong

data class DetailScreenState(
    val isLoading: Boolean = false,
    val episode: List<EpisodeSong> = emptyList(),
    val error: String = ""
)