package com.example.remote

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.local.PodcastDatabase
import com.example.mappers.toEpisodeEntity
import com.example.model.EpisodeEntity
import retrofit2.HttpException
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class PodcastRemoteMediator(
    private val podcastDb: PodcastDatabase,
    private val podcastApi: PodcastApi,
    private val showId: String,
) : RemoteMediator<Int, EpisodeEntity>() {


    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, EpisodeEntity>
    ): MediatorResult {
        return try {
            val loadKey = when (loadType) {
                LoadType.REFRESH -> 1
                LoadType.PREPEND -> return MediatorResult.Success(
                    endOfPaginationReached = true
                )

                LoadType.APPEND -> {
                    val lastItem = state.lastItemOrNull()
                    if (lastItem == null) {
                        1
                    } else {
                        (lastItem.episode_id / state.config.pageSize) + 1
                    }
                }
            }

            val episodes = podcastApi.getEpisodeForPodcast(
                limit = loadKey,
                offset = state.config.pageSize,
                showId = showId
            )
            podcastDb.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    podcastDb.dao.clearAll()
                }
                val episodeEntity = episodes.response.items.map {
                    it.toEpisodeEntity()
                }
                podcastDb.dao.upsertAll(episodeEntity)
            }
            MediatorResult.Success(
                endOfPaginationReached = episodes.response.items.isEmpty()
            )


        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        }
    }
}