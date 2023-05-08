package com.example.mappers.search

data class Show(
    val author_id: Int,
    val explicit: Boolean,
    val image_original_url: String,
    val image_url: String,
    val last_episode_at: String,
    val show_id: Int,
    val site_url: String,
    val title: String
)