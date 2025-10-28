package com.demo.domain.entity.forecast

import com.demo.domain.entity.weather.WeatherCondition
import java.time.ZonedDateTime

data class HourlyForecast(
    val timeLong: Long,
    val time: ZonedDateTime,
    val temp: Int,
    val windSpeed: Int,
    val windDirection: Int,
    val precipitation: Int,
    val localDateName: String,
    val condition: WeatherCondition,
)