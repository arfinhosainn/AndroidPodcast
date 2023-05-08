package com.example

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.repository.RemoteDataSource
import com.example.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repository: RemoteDataSource,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    var searchState = MutableStateFlow(SearchState())
        private set


    fun getSearchedEpisodes(query: String) {
        Log.d("SearchViewModel", "getSearchedEpisodes called with query: $query")
        viewModelScope.launch(Dispatchers.IO) {
            repository.getSearchedEpisodes(query).collect { result ->
                when (result) {
                    is Resource.Error -> {
                        searchState.value =
                            SearchState(
                                error = result.message ?: "An unexpected error occurred"
                            )
                    }
                    is Resource.Loading -> {
                        searchState.value = SearchState(isLoading = true)
                    }

                    is Resource.Success -> {
                        Log.d("nothing", "getSearchedEpisodes: ${result.data}")
                        withContext(Dispatchers.IO) {
                            searchState.value = SearchState(
                                episode = result.data ?: emptyList()
                            )
                        }
                    }
                }
            }
        }
    }


}