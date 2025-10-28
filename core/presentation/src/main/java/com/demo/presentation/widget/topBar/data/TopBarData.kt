package com.demo.presentation.widget.topBar.data

import android.content.Context
import androidx.compose.runtime.Immutable
import com.demo.presentation.R

@Immutable
sealed class TopBarData {

    data object Empty : TopBarData()
    data object LiveMapAndRadar : TopBarData()
    data object TropicalCenter : TopBarData()

    data class Dynamic(val name: String, val info: String? = null) : TopBarData()
    data class Place(val town: String, val state: String, val updatedMinutesAgo: String) : TopBarData()

    val title: (Context) -> String = { context ->
        when (this) {
            LiveMapAndRadar -> context.getString(R.string.live_map_and_radar)
            TropicalCenter -> context.getString(R.string.tropical_center)
            Empty -> context.getString(R.string.empty)
            is Place -> context.getString(R.string.empty)
            is Dynamic -> name
        }
    }

    val description: String?
        get() = when (this) {
            is Dynamic -> info
            else -> null
        }

}