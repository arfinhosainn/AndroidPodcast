package com.example.androidpodcast.domain.repository

import com.example.androidpodcast.data.remote.mappers.Podcast
import com.example.androidpodcast.util.Resource
import kotlinx.coroutines.flow.Flow

interface RemoteDataSource {

    fun getCuratedPodcastList(): Flow<Resource<List<Podcast>>>
}