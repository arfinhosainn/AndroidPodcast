package com.example

import com.example.core_di.remote.PodcastApi
import com.example.mappers.Episode
import com.example.mappers.toEpisodeSong
import com.example.mappers.toPodcastList
import com.example.model.EpisodeSong
import com.example.model.PodcastList
import com.example.repository.RemoteDataSource
import com.example.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(
    private val api: PodcastApi
) : RemoteDataSource {

    override fun getCuratedPodcastList(): Flow<Resource<List<PodcastList>>> = flow {
        try {
            emit(Resource.Loading())
            val response = api.getCuratedPodcastList()
            val podcasts = response.response.items.map { it.toPodcastList() }
            emit(Resource.Success(podcasts))
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "An unknown error occurred"))
        } catch (e: IOException) {
            emit(Resource.Error("Could not reach server. Check your internet connection"))
        }
    }

    override fun getEpisodeForPodcast(showId: String): Flow<Resource<List<EpisodeSong>>> = flow {
        try {
            emit(Resource.Loading())
            val response = api.getEpisodeForPodcast(showId)
            val episodes = response.response.items.map { it.toEpisodeSong() }
            emit(Resource.Success(episodes))
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "An http exception occurred"))
        } catch (e: IOException) {
            emit(Resource.Error("Could not reach server, Check your internet connection"))
        }
    }

    override fun getRecentPodcasts(): Flow<Resource<List<Episode>>> = flow {
        try {
            emit(Resource.Loading())
            val response = api.getRecentPodcast()
            val episodes = response.response.items
            emit(Resource.Success(episodes))
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "An http exception occurred"))
        } catch (e: IOException) {
            emit(Resource.Error("Could not reach server, Check your internet connection"))
        }
    }
}