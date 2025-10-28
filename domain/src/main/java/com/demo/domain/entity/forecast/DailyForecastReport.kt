package com.demo.domain.entity.forecast

import com.demo.domain.entity.weather.WeatherCondition
import java.time.LocalDate

/**
 * Domain model for daily forecast data
 */
data class DailyForecastReport(
    val date: LocalDate,
    val dayName: String,
    val dayNameShort: String,
    val temperature: Int,
    val tempMin: Int,
    val tempMax: Int,
    val feelsLike: Int?,
    val precipitation: Int,
    val precipitationChance: Int,
    val windSpeed: Float,
    val windDirection: Float,
    val windDirectionCompass: String?,
    val windGust: Float?,
    val humidity: Int?,
    val dewPoint: Int?,
    val visibility: Int?,
    val cloudCover: Int?,
    val cloudBase: Int?,
    val snow: Float?,
    val ice: Float?,
    val condition: WeatherCondition,
    val conditionTitle: String,
    val isNight: Boolean,
    val sunriseTime: Long?,
    val sunsetTime: Long?
)
