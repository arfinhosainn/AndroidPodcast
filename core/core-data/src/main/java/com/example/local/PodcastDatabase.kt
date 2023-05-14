package com.example.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.model.EpisodeEntity

@Database(
    entities = [EpisodeEntity::class], version = 1
)

abstract class PodcastDatabase : RoomDatabase() {

    abstract val dao: PodcastDao
}