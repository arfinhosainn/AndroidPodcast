package com.example.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.model.EpisodeEntity

@Dao
interface PodcastDao {

    @Upsert
    suspend fun upsertAll(episodes: List<EpisodeEntity>)

    @Query("SELECT * FROM episodeentity")
    fun pagingSource(): PagingSource<Int, EpisodeEntity>

    @Query("DELETE FROM episodeentity")
    suspend fun clearAll()

}