package com.example.androidpodcast.data.remote.repository

import android.util.Log
import com.example.androidpodcast.data.remote.PodcastApi
import com.example.androidpodcast.data.remote.mappers.Episode
import com.example.androidpodcast.data.remote.mappers.toEpisodeSong
import com.example.androidpodcast.data.remote.mappers.toPodcastList
import com.example.androidpodcast.domain.model.EpisodeSong
import com.example.androidpodcast.domain.model.PodcastList
import com.example.androidpodcast.domain.repository.RemoteDataSource
import com.example.androidpodcast.util.Resource
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okio.IOException
import retrofit2.HttpException

class RemoteDataSourceImpl @Inject constructor(
    private val api: PodcastApi
) : RemoteDataSource {

    override fun getCuratedPodcastList(): Flow<Resource<List<PodcastList>>> = flow {
        try {
            emit(Resource.Loading())
            val response = api.getCuratedPodcastList()
            val podcasts = response.response.items.map { it.toPodcastList() }
            emit(Resource.Success(podcasts))
            Log.d("podcastList", "getCuratedPodcastList: $response")
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage!!))
        } catch (e: IOException) {
            emit(Resource.Error(e.localizedMessage!!))
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
            emit(Resource.Error(e.localizedMessage ?: "An unknown error occurred"))
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
            emit(Resource.Error(e.localizedMessage ?: "An unknown error occurred"))
        }
    }
}