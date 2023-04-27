package com.example

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.repository.RemoteDataSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onCompletion
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: RemoteDataSource
) : ViewModel() {

    var homeState = MutableStateFlow(HomeScreenState())
        private set

    init {
        repository.getRecentPodcasts()
            .combine(repository.getCuratedPodcastList()) { recentPodcast, curatedList ->
                homeState.value = homeState.value.copy(
                    isLoading = true,
                    episodes = recentPodcast.data ?: emptyList(),
                    podcasts = curatedList.data ?: emptyList(),
                    podcastListError = recentPodcast.message ?: "an unknown error occurred",
                    episodeListError = curatedList.message ?: "an unknown error occurred"
                )
            }.onCompletion {
                homeState.value = homeState.value.copy(isLoading = false)
            }.launchIn(viewModelScope)
    }
}