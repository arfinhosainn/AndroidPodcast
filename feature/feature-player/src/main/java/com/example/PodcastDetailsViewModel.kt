package com.example

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.example.common.Constants.DEFAULT_POSITION_MS
import com.example.exoplayer.PodcastServiceConnection
import com.example.mappers.Episode
import com.example.mappers.toEpisode
import com.example.model.EpisodeSong
import com.example.repository.RemoteDataSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PodcastDetailsViewModel @Inject constructor(
    private val repository: RemoteDataSource,
    private val musicServiceConnection: PodcastServiceConnection,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    var detailState = MutableStateFlow(DetailScreenState())
        private set

    private val _episodes = MutableStateFlow<PagingData<Episode>>(PagingData.empty())
    val episodes: StateFlow<PagingData<Episode>> = _episodes



    init {
        savedStateHandle.get<String>("showId")?.let { showId ->
            getPodcastEpisodes(showId)
        }
    }

    private fun getPodcastEpisodes(showId: String) {
        viewModelScope.launch {
            repository.getEpisodeForPodcast(showId).cachedIn(viewModelScope)
                .collectLatest { pagingData ->
                    _episodes.value = pagingData.map { it.toEpisode() }
                }
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

    fun resume() = musicServiceConnection.play()
    fun pause() = musicServiceConnection.pause()
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


}


fun convertToPosition(value: Float, total: Long) = (value * total).toLong()