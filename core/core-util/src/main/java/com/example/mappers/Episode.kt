package com.example.mappers

import com.example.mappers.search.Show
import com.example.model.EpisodeSong

data class Episode(
    val author_id: Int,
    val download_enabled: Boolean,
    val download_url: String,
    val duration: Int,
    val episode_id: Int,
    val explicit: Boolean,
    val image_original_url: String,
    val image_transformation: String,
    val image_url: String,
    val playback_url: String,
    val published_at: String,
    val show_id: Int,
    val site_url: String,
    val stream_id: String,
    val title: String,
    val type: String,
    val waveform_url: String
)


