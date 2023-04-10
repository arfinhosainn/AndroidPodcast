package com.example.androidpodcast.data.remote.mappers

data class ResponseX(
    val items: List<Episode>,
    val next_url: String
)