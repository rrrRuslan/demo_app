package com.demo.presentation.widget.main.today

import androidx.compose.runtime.Immutable
import com.demo.presentation.R

@Immutable
data class TodayForecastData(
    val temp: String,
    val feelsLike: String,
    val isFeelsLikeVisible: Boolean,
    val description: String,
    val icon: Int,
) {
    companion object {
        fun test() = TodayForecastData(
            temp = "23",
            feelsLike = "Feels like 23",
            isFeelsLikeVisible = true,
            description = "Cloudy",
            icon = R.drawable.ic_weather_bw_mt_cloudy,
        )
    }
}