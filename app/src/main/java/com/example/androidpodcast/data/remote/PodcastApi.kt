package com.example.androidpodcast.data.remote

import com.example.androidpodcast.data.remote.mappers.PodcastDto
import retrofit2.http.GET

interface PodcastApi {

    @GET("curated_podcasts")
    suspend fun getCuratedPodcastList(): PodcastDto
}