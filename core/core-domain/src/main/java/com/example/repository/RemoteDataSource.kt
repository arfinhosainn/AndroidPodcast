package com.example.repository

import androidx.paging.PagingData
import com.example.mappers.Episode
import com.example.model.EpisodeEntity
import com.example.model.EpisodeSong
import com.example.model.PodcastList
import com.example.util.Resource
import kotlinx.coroutines.flow.Flow

interface RemoteDataSource {

    fun getCuratedPodcastList(): Flow<Resource<List<PodcastList>>>
    fun getEpisodeForPodcast(
        showId: String
    ): Flow<PagingData<EpisodeEntity>>

    fun getRecentPodcasts(): Flow<Resource<List<Episode>>>
    fun getSearchedEpisodes(query: String): Flow<Resource<List<EpisodeSong>>>
}