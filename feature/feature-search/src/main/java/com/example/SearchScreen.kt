package com.example

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.DockedSearchBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.model.EpisodeSong
import com.example.ui.theme.lightSurface
import com.example.util.toFormattedDuration


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    onTextChange: (String) -> Unit,
    searchState: SearchState,
    onPodcastClick: (EpisodeSong) -> Unit
) {

    var text by rememberSaveable { mutableStateOf("") }
    var active by rememberSaveable { mutableStateOf(false) }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            DockedSearchBar(
                modifier = Modifier.padding(top = 8.dp),
                query = text,
                onQueryChange = {
                    text = it
                    onTextChange(it)
                },
                onSearch = {
                    onTextChange(text)
                },
                active = active,
                onActiveChange = { active = it },
                placeholder = { Text("Search Episodes") },
                trailingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                colors = SearchBarDefaults.colors(
                    containerColor = lightSurface,

                    )
            ) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    items(searchState.episode) { episodes ->

                        ListItem(
                            headlineContent = {
                                Text(
                                    episodes.title,
                                    color = MaterialTheme.colorScheme.inverseSurface
                                )
                            },
                            supportingContent = { Text(episodes.duration.toFormattedDuration()) },
                            leadingContent = {
                                AsyncImage(
                                    model = episodes.image_url,
                                    contentDescription = "",
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(10.dp))
                                        .size(70.dp)
                                )
                            },
                            modifier = Modifier.clickable {
                                onPodcastClick(episodes)
                                active = false
                            },
                            colors = ListItemDefaults.colors(containerColor = lightSurface)
                        )
                    }
                }
            }
        }
    }

}