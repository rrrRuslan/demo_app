package com.demo.presentation.widget.main.forecast.daily

import androidx.compose.runtime.Immutable
import com.demo.presentation.widget.main.forecast.hourly.ForecastHourlyItemData

@Immutable
data class ForecastDailyItemData(
    val day: String,
    val precipitation: String,
    val tempMin: String,
    val tempMax: String,
    val icon: Int,
    val hourlyList: List<ForecastHourlyItemData>,
    val isExpanded: Boolean,
) {
    companion object {
        fun testList(): List<ForecastDailyItemData> {
            return listOf(
                ForecastDailyItemData(
                    day = "Monday",
                    precipitation = "12",
                    tempMin = "12°",
                    tempMax = "12°",
                    icon = 0,
                    hourlyList = ForecastHourlyItemData.testList(),
                    isExpanded = false
                ),
                ForecastDailyItemData(
                    day = "Tuesday",
                    precipitation = "13",
                    tempMin = "13°",
                    tempMax = "13°",
                    icon = 0,
                    hourlyList = ForecastHourlyItemData.testList(),
                    isExpanded = false
                ),
                ForecastDailyItemData(
                    day = "Wednesday",
                    precipitation = "14",
                    tempMin = "14°",
                    tempMax = "14°",
                    icon = 0,
                    hourlyList = ForecastHourlyItemData.testList(),
                    isExpanded = false
                ),
                ForecastDailyItemData(
                    day = "Thursday",
                    precipitation = "15",
                    tempMin = "15°",
                    tempMax = "15°",
                    icon = 0,
                    hourlyList = ForecastHourlyItemData.testList(),
                    isExpanded = false
                ),
                ForecastDailyItemData(
                    day = "Friday",
                    precipitation = "16",
                    tempMin = "16°",
                    tempMax = "16°",
                    icon = 0,
                    hourlyList = ForecastHourlyItemData.testList(),
                    isExpanded = false
                )
            )
        }
    }
}