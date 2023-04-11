package com.example.androidpodcast.media

import javax.inject.Qualifier
import kotlin.annotation.AnnotationRetention.RUNTIME

@Qualifier
@Retention(RUNTIME)
annotation class Dispatcher(val loulaDispatcher: PodcastDispatcher)

enum class PodcastDispatcher { DEFAULT, MAIN, UNCONFINED, IO }