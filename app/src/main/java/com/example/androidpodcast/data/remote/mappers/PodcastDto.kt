package com.example.androidpodcast.data.remote.mappers

data class PodcastDto(
    val curated_lists: List<CuratedLists>,
    val has_next: Boolean,
    val has_previous: Boolean,
    val next_page_number: Int,
    val page_number: Int,
    val previous_page_number: Int,
    val total: Int
)