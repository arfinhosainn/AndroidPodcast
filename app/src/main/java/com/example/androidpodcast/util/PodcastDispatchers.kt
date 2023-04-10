package com.example.androidpodcast.util

import javax.inject.Qualifier
import kotlin.annotation.AnnotationRetention.RUNTIME

@Qualifier
@Retention(RUNTIME)
annotation class Dispatcher(val podcastDispatchers: PodcastDispatchers)

enum class PodcastDispatchers { DEFAULT, MAIN, UNCONFINED, IO }