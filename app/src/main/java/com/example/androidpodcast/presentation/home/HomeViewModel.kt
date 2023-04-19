package com.example.androidpodcast.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidpodcast.domain.repository.RemoteDataSource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.*

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