package com.demo.presentation.widget.main.chart.data

import com.demo.domain.entity.forecast.DailyForecastReport
import com.demo.domain.entity.weather.WeatherCondition
import java.time.LocalDate

/**
 * Data class for daily forecast data point
 */
data class DailyForecastPoint(
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

/**
 * Data class for aggregated daily weather data
 */
data class AggregatedDailyWeatherData(
    val date: LocalDate,
    val dayName: String,
    val dayNameShort: String,
    val temperature: DailyWeatherStatData,
    val feelsLike: DailyWeatherStatData?,
    val precipitation: DailyWeatherStatData,
    val windSpeed: DailyWeatherStatData,
    val windGust: DailyWeatherStatData?,
    val humidity: DailyWeatherStatData?,
    val dewPoint: DailyWeatherStatData?,
    val visibility: DailyWeatherStatData?,
    val cloudCover: DailyWeatherStatData?,
    val cloudBase: DailyWeatherStatData?,
    val precipitationChance: DailyWeatherStatData?,
    val snow: DailyWeatherStatData?,
    val ice: DailyWeatherStatData?,
    val dominantCondition: WeatherCondition,
    val isNight: Boolean,
    val sunriseTime: Long?,
    val sunsetTime: Long?
)

/**
 * Data class for daily weather statistics
 */
data class DailyWeatherStatData(
    val min: Float,
    val max: Float,
    val total: Float? = null // For cumulative values like precipitation
)

/**
 * Main daily chart data container
 */
data class DailyForecastChartData(
    val dataPoints: List<AggregatedDailyWeatherData>,
    val availableStats: Set<WeatherStatistic>,
    val startDate: LocalDate,
    val endDate: LocalDate
) {
    init {
        // Ensure we have valid data points
        require(dataPoints.isNotEmpty()) { "DailyForecastChartData must have at least one data point" }
        require(dataPoints.all { it.temperature.min > -999 && it.temperature.max > -999 }) { 
            "All temperature data must be valid (greater than -999)" 
        }
    }
}

/**
 * Extension functions to convert DailyForecastReport to daily chart data
 */
fun List<DailyForecastReport>.toDailyForecastPoints(): List<DailyForecastPoint> {
    return mapNotNull { report ->
        // Filter out reports with invalid temperature data
        if (report.tempMin <= -999 || report.tempMax <= -999) {
            return@mapNotNull null
        }
        
        DailyForecastPoint(
            date = report.date,
            dayName = report.dayName,
            dayNameShort = report.dayNameShort,
            temperature = report.temperature,
            tempMin = report.tempMin,
            tempMax = report.tempMax,
            feelsLike = report.feelsLike,
            precipitation = report.precipitation,
            precipitationChance = report.precipitationChance,
            windSpeed = report.windSpeed,
            windDirection = report.windDirection,
            windDirectionCompass = report.windDirectionCompass,
            windGust = report.windGust,
            humidity = report.humidity,
            dewPoint = report.dewPoint,
            visibility = report.visibility,
            cloudCover = report.cloudCover,
            cloudBase = report.cloudBase,
            snow = report.snow,
            ice = report.ice,
            condition = report.condition,
            conditionTitle = report.conditionTitle,
            isNight = report.isNight,
            sunriseTime = report.sunriseTime,
            sunsetTime = report.sunsetTime
        )
    }
}
