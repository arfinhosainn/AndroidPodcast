package com.example.androidpodcast.data.remote

import com.example.androidpodcast.data.remote.mappers.Podcasts
import com.example.androidpodcast.data.remote.mappers.Show
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PodcastApi {

    @GET("explore/lists/108/items")
    suspend fun getCuratedPodcastList(
        @Query("limit") limit: Int = 60
    ): Podcasts

    @GET("shows/{showId}/episodes")
    suspend fun getEpisodeForPodcast(
        @Path("showId") showId: String
    ): Show

    @GET("tags/android+development/episodes")
    suspend fun getRecentPodcast(
        @Query("limit") limit: Int = 20
    ): Show
}