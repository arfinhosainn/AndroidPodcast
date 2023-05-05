package com.example.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import com.example.feature_player.R
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlayerTopBar(
    onMenuClick: () -> Unit,
    onBackPressed: () -> Unit
) {
    TopAppBar(
        navigationIcon = {
            IconButton(onClick = onBackPressed) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Search Icon",
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }
        },
        actions = {
            IconButton(onClick = onBackPressed) {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.donwloadvc),
                    contentDescription = "Search Icon",
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }
        },
        title = {}
    )
}