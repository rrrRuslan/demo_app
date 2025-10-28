package com.demo.presentation.widget.main.skyCams.data

import androidx.compose.runtime.Immutable
import com.demo.presentation.widget.main.mediaContent.data.MediaType

@Immutable
data class SkyCamsData(
    val imageUrl: String,
    val mediaType: MediaType,
    val location: String,
    val state: String,
    val lastUpdateTime: Long?,
    val lastUpdateTimeFormatted: String,
)
