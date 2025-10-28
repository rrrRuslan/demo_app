package com.demo.domain.entity.forecast

import com.demo.domain.entity.weather.WeatherCondition
import java.time.LocalDate

data class DailyForecast(
    val date: LocalDate,
    val dayName: String,
    val tempMin: Int,
    val tempMax: Int,
    val precipitation: Int,
    val condition: WeatherCondition,
    val hourlyList: MutableList<HourlyForecast>,
)