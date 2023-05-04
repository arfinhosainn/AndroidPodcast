package com.example

import com.example.model.EpisodeSong

data class SearchState(
    val isLoading: Boolean = false,
    val episode: List<EpisodeSong> = emptyList(),
    val error: String = ""
)