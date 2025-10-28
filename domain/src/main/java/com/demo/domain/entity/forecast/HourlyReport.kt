package com.demo.domain.entity.forecast

import com.demo.domain.entity.weather.WeatherCondition
import java.time.ZonedDateTime

data class HourlyReport(
    val timeLong: Long,
    val time: ZonedDateTime,
    val temp: Int,
    val windSpeed: Int,
    val windDirection: Int,
    val precipitation: Int,
    val localDateName: String,
    val conditionTitle: String,
    val condition: WeatherCondition,
    val feelsLike: Int?,
    val windDirectionCompass: String,
    val windGust: Float?,
    val humidity: Int?,
    val dewPoint: Int?,
    val visibility: Int?,
    val cloudCover: Int?,
    val cloudBase: Int?,
    val precipitationHour: Float?,
    val precipitationChance: Int?,
    val snow: Float?,
    val ice: Float?
)