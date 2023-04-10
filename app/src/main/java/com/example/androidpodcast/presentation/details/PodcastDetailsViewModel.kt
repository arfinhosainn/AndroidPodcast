package com.example.androidpodcast.presentation.details

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidpodcast.domain.repository.RemoteDataSource
import com.example.androidpodcast.media.MusicServiceConnection
import com.example.androidpodcast.util.MediaConstants
import com.example.androidpodcast.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.*

@HiltViewModel
class PodcastDetailsViewModel @Inject constructor(
    private val repository: RemoteDataSource,
    private val musicServiceConnection: MusicServiceConnection,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    var detailState = MutableStateFlow(DetailScreenState())
        private set

    val musicState = musicServiceConnection.musicState
    val currentPosition = musicServiceConnection.currentPosition.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Lazily,
        initialValue = MediaConstants.DEFAULT_POSITION_MS
    )

    init {
        savedStateHandle.get<String>("showId")?.let { showId ->
            getPodcastEpisodes(showId)
        }
        Log.d("musicservice", ":${musicServiceConnection.play()} ")
    }

    fun skipPrevious() = musicServiceConnection.skipPrevious()
    fun play() = musicServiceConnection.play()

    fun pause() = musicServiceConnection.pause()
    fun skipNext() = musicServiceConnection.skipNext()
    fun skipTo(position: Float) =
        musicServiceConnection.skipTo(convertToPosition(position, musicState.value.duration))

    private fun getPodcastEpisodes(showId: String) {
        repository.getEpisodeForPodcast(showId).onEach { result ->
            when (result) {
                is Resource.Error -> {
                    detailState.value =
                        DetailScreenState(error = result.message ?: "An unexpected error occurred")
                }
                is Resource.Loading -> {
                    detailState.value = DetailScreenState(isLoading = true)
                }
                is Resource.Success -> {
                    detailState.value = DetailScreenState(episode = result.data ?: emptyList())
                }
            }
        }.launchIn(viewModelScope)
    }
}

fun convertToPosition(value: Float, total: Long) = (value * total).toLong()