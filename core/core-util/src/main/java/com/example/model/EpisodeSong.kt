package com.example.model

data class EpisodeSong(
    val author_id: Int,
    val episode_id: Int,
    val image_original_url: String,
    val image_url: String,
    val playback_url: String,
    val published_at: String,
    val show_id: Int,
    val title: String,
    val download_url: String,
    val duration: Int
)
