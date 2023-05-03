package com.example.repository

import com.example.mappers.Episode
import com.example.model.EpisodeSong
import com.example.model.PodcastList
import com.example.util.Resource
import kotlinx.coroutines.flow.Flow

interface RemoteDataSource {

    fun getCuratedPodcastList(): Flow<Resource<List<PodcastList>>>
    fun getEpisodeForPodcast(showId: String): Flow<Resource<List<EpisodeSong>>>
    fun getRecentPodcasts(): Flow<Resource<List<Episode>>>
    fun getSearchedEpisodes(query: String): Flow<Resource<List<Episode>>>
}