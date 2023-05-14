package com.example.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.mappers.search.Show

@Entity
data class EpisodeEntity(
    val author_id: Int,
    val download_enabled: Boolean,
    val download_url: String,
    val duration: Int,
    @PrimaryKey
    val episode_id: Int,
    val explicit: Boolean,
    val image_original_url: String,
    val image_transformation: String,
    val image_url: String,
    val playback_url: String,
    val published_at: String,
    val show_id: Int,
    val site_url: String,
    val title: String,
    val type: String,
    val waveform_url: String
)

