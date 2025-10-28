package com.demo.presentation.widget.main.forecast.hourly

import androidx.compose.runtime.Immutable

@Immutable
data class ForecastHourlyItemData(
    val timeLong: Long,
    val date: String,
    val time: String,
    val temp: String,
    val windSpeed: String,
    val windAngle: Float,
    val precipitation: String,
    val icon: Int,
    val isActive: Boolean = false,
) {
    companion object {
        fun testList(): List<ForecastHourlyItemData> {
            return listOf(
                ForecastHourlyItemData(
                    timeLong = 0,
                    date = "",
                    time = "12:00",
                    temp = "12°",
                    windSpeed = "12",
                    windAngle = 0f,
                    precipitation = "12",
                    icon = 0,
                    isActive = true
                ),
                ForecastHourlyItemData(
                    timeLong = 0,
                    date = "",
                    time = "13:00",
                    temp = "13°",
                    windSpeed = "13",
                    windAngle = 0f,
                    precipitation = "13",
                    icon = 0,
                    isActive = false
                ),
                ForecastHourlyItemData(
                    timeLong = 0,
                    date = "",
                    time = "14:00",
                    temp = "14°",
                    windSpeed = "14",
                    windAngle = 0f,
                    precipitation = "14",
                    icon = 0,
                    isActive = false
                ),
                ForecastHourlyItemData(
                    timeLong = 0,
                    date = "",
                    time = "15:00",
                    temp = "15°",
                    windSpeed = "15",
                    windAngle = 0f,
                    precipitation = "15",
                    icon = 0,
                    isActive = false
                ),
                ForecastHourlyItemData(
                    timeLong = 0,
                    date = "",
                    time = "16:00",
                    temp = "16°",
                    windSpeed = "16",
                    windAngle = 0f,
                    precipitation = "16",
                    icon = 0,
                    isActive = false
                )
            )
        }
    }
}
