package com.example.androidpodcast.data.remote.mappers

data class CuratedLists(
    val description: String,
    val id: String,
    val listennotes_url: String,
    val podcasts: List<Podcast>,
    val pub_date_ms: Long,
    val source_domain: String,
    val source_url: String,
    val title: String,
    val total: Int
)