package com.example

import com.example.mappers.Episode

data class SearchState(
    val isLoading: Boolean = false,
    val episode: List<Episode> = emptyList(),
    val error: String = ""
)