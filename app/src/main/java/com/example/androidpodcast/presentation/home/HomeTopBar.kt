package com.example.androidpodcast.presentation.home

import androidx.compose.foundation.Image
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.androidpodcast.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeTopBar(
    onMenuClick: () -> Unit,
    onSearchBarClicked: () -> Unit
) {
    TopAppBar(
        title = {
            Image(
                imageVector = ImageVector.vectorResource(id = R.drawable.logo),
                contentDescription = "logo"
            )
        },
        actions = {
            IconButton(onClick = onSearchBarClicked) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search Icon",
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }
            IconButton(onClick = onMenuClick) {
                Icon(
                    imageVector = Icons.Default.Menu,
                    contentDescription = "Menu Icon",
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }
        }
    )
}

@Preview
@Composable
fun PreviewHomeTopBar() {
    HomeTopBar(onMenuClick = { /*TODO*/ }) {
    }
}