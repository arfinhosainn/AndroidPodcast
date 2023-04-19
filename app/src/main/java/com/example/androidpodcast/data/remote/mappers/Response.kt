package com.example.androidpodcast.data.remote.mappers

data class Response(

    val items: List<PodcastListDto>,
    val next_url: Any

)