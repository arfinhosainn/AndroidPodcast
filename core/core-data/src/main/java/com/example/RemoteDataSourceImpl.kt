package com.example

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.RemoteMediator
import com.example.local.PodcastDatabase
import com.example.remote.PodcastApi
import com.example.mappers.Episode
import com.example.mappers.toEpisodeSong
import com.example.mappers.toPodcastList
import com.example.model.EpisodeEntity
import com.example.model.EpisodeSong
import com.example.model.PodcastList
import com.example.remote.PodcastRemoteMediator
import com.example.repository.RemoteDataSource
import com.example.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(
    private val api: PodcastApi,
    private val db: PodcastDatabase
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

    @OptIn(ExperimentalPagingApi::class)
    override fun getEpisodeForPodcast(showId: String): Flow<PagingData<EpisodeEntity>> {
        val pagingSourceFactory = { db.dao.pagingSource() }
        return Pager(
            config = PagingConfig(pageSize = 5),
            remoteMediator =
            PodcastRemoteMediator(
                podcastDb = db,
                podcastApi = api,
                showId = showId
            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow
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

    override fun getSearchedEpisodes(query: String): Flow<Resource<List<EpisodeSong>>> = flow {
        try {
            emit(Resource.Loading())
            val response = api.getSearchedEpisodes(q = query)
            val episodes = response.response.items.map { it.toEpisodeSong() }
            Log.d("newPodcast", "getSearchedEpisodes: $episodes")
            emit(Resource.Success(episodes))
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "An http exception occurred"))
        } catch (e: IOException) {
            emit(Resource.Error("Could not reach server, Check your internet connection"))
        }
    }
}