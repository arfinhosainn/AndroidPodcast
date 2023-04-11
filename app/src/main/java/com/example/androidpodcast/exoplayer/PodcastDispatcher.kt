package com.example.androidpodcast.exoplayer

import javax.inject.Qualifier
import kotlin.annotation.AnnotationRetention.RUNTIME

@Qualifier
@Retention(RUNTIME)
annotation class Dispatcher(val podcastDispatcher: PodcastDispatcher)

enum class PodcastDispatcher { DEFAULT, MAIN, UNCONFINED, IO }