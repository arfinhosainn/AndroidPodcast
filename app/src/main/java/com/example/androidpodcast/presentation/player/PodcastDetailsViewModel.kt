package com.example.androidpodcast.presentation.player

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidpodcast.domain.model.EpisodeSong
import com.example.androidpodcast.domain.repository.RemoteDataSource
import com.example.androidpodcast.exoplayer.PodcastServiceConnection
import com.example.androidpodcast.exoplayer.common.Constants.DEFAULT_POSITION_MS
import com.example.androidpodcast.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@HiltViewModel
class PodcastDetailsViewModel @Inject constructor(
    private val repository: RemoteDataSource,
    private val musicServiceConnection: PodcastServiceConnection,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    var detailState = MutableStateFlow(DetailScreenState())
        private set

    init {
        savedStateHandle.get<String>("showId")?.let { showId ->
            getPodcastEpisodes(showId)
        }
    }

    val musicState = musicServiceConnection.musicState
    val currentPosition = musicServiceConnection.currentPosition.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = DEFAULT_POSITION_MS
    )

    fun playPodcast(episodeSong: List<EpisodeSong>) {
        viewModelScope.launch(Dispatchers.Main) {
            musicServiceConnection.playSongs(
                songs = episodeSong
            )
        }
    }

    fun skipPrevious() = musicServiceConnection.skipPrevious()
    fun resume() = musicServiceConnection.play()
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
                    withContext(Dispatchers.IO) {
                        detailState.value = DetailScreenState(episode = result.data ?: emptyList())
                    }
                }
            }
        }.launchIn(viewModelScope)
    }
}

fun convertToPosition(value: Float, total: Long) = (value * total).toLong()