package com.demo.presentation.widget.main.details

import androidx.compose.runtime.Immutable

@Immutable
data class ForecastDetailsItemData(
    val dewpoint: String,
    val humidity: String,
    val windChill: String,
    val heatIndex: String,
    val pressure: String,
    val precip: String,
    val winds: String,
    val station: String,
) {
    companion object {
        fun test() = ForecastDetailsItemData(
            dewpoint = "13°",
            humidity = "13",
            windChill = "13°",
            heatIndex = "13°",
            pressure = "13",
            precip = "13",
            winds = "13",
            station = "13"
        )
    }
}