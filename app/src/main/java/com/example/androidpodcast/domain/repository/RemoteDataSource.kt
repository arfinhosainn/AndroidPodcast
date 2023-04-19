package com.example.androidpodcast.domain.repository

import com.example.androidpodcast.data.remote.mappers.Episode
import com.example.androidpodcast.domain.model.EpisodeSong
import com.example.androidpodcast.domain.model.PodcastList
import com.example.androidpodcast.util.Resource
import kotlinx.coroutines.flow.Flow

interface RemoteDataSource {

    fun getCuratedPodcastList(): Flow<Resource<List<PodcastList>>>
    fun getEpisodeForPodcast(showId: String): Flow<Resource<List<EpisodeSong>>>

    fun getRecentPodcasts(): Flow<Resource<List<Episode>>>
}