package com.example

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
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
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    onTextChange: (String) -> Unit,
    searchState: SearchState
) {
    var text by rememberSaveable { mutableStateOf("") }
    var active by rememberSaveable { mutableStateOf(false) }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 50.dp),
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
            placeholder = { Text("Hinted search text") },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
            trailingIcon = { Icon(Icons.Default.MoreVert, contentDescription = null) },
            colors = SearchBarDefaults.colors(
                    containerColor = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.4f)
            )
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.onBackground.copy(alpha = 0.4f)),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                items(searchState.episode) { episodes ->

                    ListItem(
                        headlineContent = { Text(episodes.title) },
                        supportingContent = { Text(episodes.author_id.toString()) },
                        leadingContent = { Icon(Icons.Filled.Star, contentDescription = null) },
                        modifier = Modifier.clickable {
                            text = episodes.title
                            active = false
                        },
                        colors = ListItemDefaults.colors(containerColor = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.4f))
                    )
                }
            }
        }

    }
}


//@Preview
//@Composable
//fun PreviewSearchScreen() {
//    SearchScreen()
//
//}
//
