package com.example

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.common.Constants.DEFAULT_POSITION_MS
import com.example.exoplayer.PodcastServiceConnection
import com.example.model.EpisodeSong
import com.example.repository.RemoteDataSource
import com.example.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

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
    var currentPosition = musicServiceConnection.currentPosition.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = DEFAULT_POSITION_MS
    )


    fun playPodcast(episodeSong: List<EpisodeSong>) {
        viewModelScope.launch(Dispatchers.Main) {
            musicServiceConnection.playSongs(
                songs = episodeSong,
                startIndex = 0
            )
        }
    }

    fun skipPrevious() = musicServiceConnection.skipPrevious()
    fun resume() = musicServiceConnection.play()
    fun pause() = musicServiceConnection.pause()
    fun skipNext() = musicServiceConnection.skipNext()
    private var currentPositions = 0L

    fun seekTo10Seconds() {
        val totalDuration = musicState.value.duration
        currentPositions += (totalDuration * 0.01).toLong()
        musicServiceConnection.seekTo10Seconds(currentPositions)
    }
    fun seekBackwards10Seconds() {
        val totalDuration = musicState.value.duration
        currentPositions -= (totalDuration / 0.01).toLong()
        musicServiceConnection.seekTo10Seconds(currentPositions)
    }





    fun skipTo(position: Float) =
        musicServiceConnection.skipTo(convertToPosition(position, musicState.value.duration))

    private fun getPodcastEpisodes(showId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getEpisodeForPodcast(showId).collect { result ->
                when (result) {
                    is Resource.Error -> {
                        detailState.value =
                            DetailScreenState(
                                error = result.message ?: "An unexpected error occurred"
                            )
                    }

                    is Resource.Loading -> {
                        detailState.value = DetailScreenState(isLoading = true)
                    }

                    is Resource.Success -> {
                        withContext(Dispatchers.IO) {
                            detailState.value = DetailScreenState(
                                episode = result.data ?: emptyList()
                            )
                        }
                    }
                }
            }
        }
    }
}


fun convertToPosition(value: Float, total: Long) = (value * total).toLong()