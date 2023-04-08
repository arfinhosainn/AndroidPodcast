package com.example.androidpodcast.data.remote.mappers

data class Podcast(
    val id: String,
    val image: String,
    val listen_score: String,
    val listen_score_global_rank: String,
    val listennotes_url: String,
    val publisher: String,
    val thumbnail: String,
    val title: String
)