package com.example.androidpodcast.presentation.details.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.androidpodcast.util.asFormattedString
import com.example.androidpodcast.util.convertToProgress

@Composable
internal fun PlayerTimeSlider(
    currentPosition: Long,
    duration: Long,
    onSkipTo: (Float) -> Unit,
    modifier: Modifier = Modifier
) {
    val progress by animateFloatAsState(
        targetValue = convertToProgress(count = currentPosition, total = duration)
    )

    Column(
        modifier = modifier
            .padding(horizontal = 20.dp)
            .fillMaxWidth()
    ) {
        Slider(
            modifier = Modifier.fillMaxWidth(),
            value = progress,
            onValueChange = onSkipTo
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = currentPosition.asFormattedString(),
                style = MaterialTheme.typography.bodySmall,
                color = TimeTextColor
            )
            Text(
                text = duration.asFormattedString(),
                style = MaterialTheme.typography.bodySmall,
                color = TimeTextColor
            )
        }
    }
}

private val TimeTextColor = Color.Gray