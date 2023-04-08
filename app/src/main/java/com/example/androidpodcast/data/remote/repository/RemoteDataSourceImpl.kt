package com.example.androidpodcast.data.remote.repository

import android.util.Log
import com.example.androidpodcast.data.remote.PodcastApi
import com.example.androidpodcast.data.remote.mappers.Podcast
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

    override fun getCuratedPodcastList(): Flow<Resource<List<Podcast>>> = flow {
        try {
            emit(Resource.Loading())
            val response = api.getCuratedPodcastList()
            val podcasts = response.curated_lists.flatMap { it.podcasts }
            emit(Resource.Success(podcasts))
            Log.d("podcastList", "getCuratedPodcastList: $response")
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "An http exception occurred"))
        } catch (e: IOException) {
            emit(Resource.Error(e.localizedMessage ?: "An unknown error occurred"))
        }
    }
}