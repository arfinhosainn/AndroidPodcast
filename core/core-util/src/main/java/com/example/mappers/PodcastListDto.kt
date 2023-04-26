package com.example.mappers

import com.example.model.PodcastList

data class PodcastListDto(
    val author_id: Int,
    val explicit: Boolean,
    val image_original_url: String,
    val image_url: String,
    val last_episode_at: String,
    val show_id: Int,
    val site_url: String,
    val title: String
)

fun PodcastListDto.toPodcastList(): PodcastList {
    return PodcastList(
        image_original_url = image_original_url,
        show_id = show_id,
        last_episode_at = last_episode_at,
        title = title
    )
}