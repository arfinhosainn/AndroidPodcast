package com.example.androidpodcast.presentation.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidpodcast.domain.repository.RemoteDataSource
import com.example.androidpodcast.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: RemoteDataSource
) : ViewModel() {

    var curatePodcastList = MutableStateFlow(HomeScreenState())
        private set

    init {
        getCuratedListOfPodcast()
    }

    fun getCuratedListOfPodcast() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getCuratedPodcastList().collect { result ->
                when (result) {
                    is Resource.Error -> {
                        curatePodcastList.value = HomeScreenState(
                            error = result.message ?: "An unknown error occurred"
                        )
                    }
                    is Resource.Loading -> {
                        curatePodcastList.value = HomeScreenState(isLoading = true)
                    }
                    is Resource.Success -> {
                        curatePodcastList.value = HomeScreenState(
                            podcasts = result.data ?: emptyList()
                        )
                        Log.d("podcastList", "getCuratedListOfPodcast: ${result.data}")
                    }
                }
            }
        }
    }
}