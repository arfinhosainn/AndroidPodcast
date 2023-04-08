package com.example.androidpodcast.presentation.home

import com.example.androidpodcast.data.remote.mappers.Podcast

data class HomeScreenState(
    val isLoading: Boolean = false,
    val podcasts: List<Podcast> = emptyList(),
    val error: String = ""
)