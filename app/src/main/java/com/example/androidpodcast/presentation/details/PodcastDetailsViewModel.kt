package com.example.androidpodcast.presentation.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidpodcast.domain.repository.RemoteDataSource
import com.example.androidpodcast.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@HiltViewModel
class PodcastDetailsViewModel @Inject constructor(
    private val repository: RemoteDataSource,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    var detailState = MutableStateFlow(DetailScreenState())
        private set

    init {
        savedStateHandle.get<String>("showId")?.let { showId ->
            getPodcastEpisodes(showId)
        }
    }

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