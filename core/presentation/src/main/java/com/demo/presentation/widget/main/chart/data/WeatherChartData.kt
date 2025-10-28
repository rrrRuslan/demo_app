package com.demo.presentation.widget.main.chart.data

import com.demo.domain.entity.common.Units
import com.demo.presentation.widget.main.chart.data.WeatherStatistic.CLOUD_BASE
import com.demo.presentation.widget.main.chart.data.WeatherStatistic.CLOUD_COVER
import com.demo.presentation.widget.main.chart.data.WeatherStatistic.DEW_POINT
import com.demo.presentation.widget.main.chart.data.WeatherStatistic.FEELS_LIKE
import com.demo.presentation.widget.main.chart.data.WeatherStatistic.HUMIDITY
import com.demo.presentation.widget.main.chart.data.WeatherStatistic.ICE
import com.demo.presentation.widget.main.chart.data.WeatherStatistic.PRECIPITATION
import com.demo.presentation.widget.main.chart.data.WeatherStatistic.PRECIPITATION_CHANCE
import com.demo.presentation.widget.main.chart.data.WeatherStatistic.SNOW
import com.demo.presentation.widget.main.chart.data.WeatherStatistic.TEMPERATURE
import com.demo.presentation.widget.main.chart.data.WeatherStatistic.VISIBILITY
import com.demo.presentation.widget.main.chart.data.WeatherStatistic.WIND_GUST
import com.demo.presentation.widget.main.chart.data.WeatherStatistic.WIND_SPEED
import com.demo.domain.entity.forecast.HourlyReport
import com.demo.domain.entity.weather.WeatherCondition
import java.time.LocalDate
import java.time.ZonedDateTime

/**
 * Enum representing different time periods for the chart
 */
enum class ChartTimePeriod(val label: String, val hours: Int) {
    THREE_HOUR_24("24 Hours (3h)", 24),
    SIX_HOUR_24("24 Hours (6h)", 24),
    DAILY_WEEK("7 Days", 168)
}

/**
 * Enum representing different weather statistics that can be displayed
 */
enum class WeatherStatistic(val label: String, val unit: String, val color: Long) {
    TEMPERATURE("Temperature", "°F", 0xFF2196F3), // Blue
    FEELS_LIKE("Feels Like", "°F", 0xFF4CAF50), // Green
    PRECIPITATION("Precipitation", "mm", 0xFF2196F3), // Blue
    WIND_SPEED("Wind Speed", "mph", 0xFF9C27B0), // Purple
    WIND_GUST("Wind Gust", "mph", 0xFFE91E63), // Pink
    HUMIDITY("Humidity", "%", 0xFF00BCD4), // Cyan
    DEW_POINT("Dew Point", "°F", 0xFF795548), // Brown
    VISIBILITY("Visibility", "mi", 0xFFFF9800), // Orange
    CLOUD_COVER("Cloud Cover", "%", 0xFF607D8B), // Blue Grey
    CLOUD_BASE("Cloud Base", "m", 0xFF795548), // Brown
    PRECIPITATION_CHANCE("Precip Chance", "%", 0xFF2196F3), // Blue
    SNOW("Snow", "in", 0xFFE3F2FD), // Light Blue
    ICE("Ice", "in", 0xFFF3E5F5) // Light Purple
}

/**
 * Extension function to get the appropriate unit based on the current Units setting
 */
fun WeatherStatistic.getUnitFor(units: Units): String {
    return when (this) {
        TEMPERATURE, FEELS_LIKE, DEW_POINT -> {
            when (units) {
                Units.English -> "°F"
                Units.Metric -> "°C"
            }
        }
        WIND_SPEED, WIND_GUST -> {
            when (units) {
                Units.English -> "mph"
                Units.Metric -> "km/h"
            }
        }
        VISIBILITY -> {
            when (units) {
                Units.English -> "mi"
                Units.Metric -> "km"
            }
        }
        CLOUD_BASE -> {
            when (units) {
                Units.English -> "ft"
                Units.Metric -> "m"
            }
        }
        SNOW, ICE -> {
            when (units) {
                Units.English -> "in"
                Units.Metric -> "cm"
            }
        }
        else -> unit // For parameters that don't change (%, mm, etc.)
    }
}

/**
 * Data class for a single data point in the chart
 */
data class WeatherChartPoint(
    val time: ZonedDateTime,
    val hour: Int,
    val temperature: Float,
    val feelsLike: Float?,
    val precipitation: Float,
    val windSpeed: Float,
    val windGust: Float?,
    val humidity: Float?,
    val dewPoint: Float?,
    val visibility: Float?,
    val cloudCover: Float?,
    val cloudBase: Float?,
    val precipitationChance: Float?,
    val snow: Float?,
    val ice: Float?,
    val condition: WeatherCondition
)

/**
 * Data class for aggregated weather data over a time period
 */
data class AggregatedWeatherData(
    val startTime: ZonedDateTime,
    val endTime: ZonedDateTime,
    val periodLabel: String,
    val temperature: WeatherStatData,
    val feelsLike: WeatherStatData?,
    val precipitation: WeatherStatData,
    val windSpeed: WeatherStatData,
    val windGust: WeatherStatData?,
    val humidity: WeatherStatData?,
    val dewPoint: WeatherStatData?,
    val visibility: WeatherStatData?,
    val cloudCover: WeatherStatData?,
    val cloudBase: WeatherStatData?,
    val precipitationChance: WeatherStatData?,
    val snow: WeatherStatData?,
    val ice: WeatherStatData?,
    val dominantCondition: WeatherCondition
)

/**
 * Data class for statistical data of a weather parameter
 */
data class WeatherStatData(
    val min: Float,
    val max: Float,
    val total: Float? = null // For cumulative values like precipitation
)

/**
 * Main chart data container
 */
data class EnhancedWeatherChartData(
    val timePeriod: ChartTimePeriod,
    val dataPoints: List<AggregatedWeatherData>,
    val availableStats: Set<WeatherStatistic>,
    val startDate: LocalDate,
    val endDate: LocalDate
)

/**
 * Extension functions to convert HourlyReport to chart data
 */
fun List<HourlyReport>.toWeatherChartPoints(): List<WeatherChartPoint> {
    return map { report ->
        WeatherChartPoint(
            time = report.time,
            hour = report.time.hour,
            temperature = report.temp.toFloat(),
            feelsLike = report.feelsLike?.toFloat(),
            precipitation = report.precipitation.toFloat(),
            windSpeed = report.windSpeed.toFloat(),
            windGust = report.windGust,
            humidity = report.humidity?.toFloat(),
            dewPoint = report.dewPoint?.toFloat(),
            visibility = report.visibility?.toFloat(),
            cloudCover = report.cloudCover?.toFloat(),
            cloudBase = report.cloudBase?.toFloat(),
            precipitationChance = report.precipitationChance?.toFloat(),
            snow = report.snow,
            ice = report.ice,
            condition = report.condition
        )
    }
}

/**
 * Aggregate hourly data into different time periods
 */
fun List<WeatherChartPoint>.aggregateByPeriod(period: ChartTimePeriod): List<AggregatedWeatherData> {
    return when (period) {
        ChartTimePeriod.THREE_HOUR_24 -> aggregateByHours(3)
        ChartTimePeriod.SIX_HOUR_24 -> aggregateByHours(6)
        ChartTimePeriod.DAILY_WEEK -> aggregateDaily()
    }
}



private fun List<WeatherChartPoint>.aggregateByHours(hours: Int): List<AggregatedWeatherData> {
    val dataPoints = when (hours) {
        6 -> 5 // Special case: 6-hour chart shows 5 data points
        else -> 24 / hours
    }
    
    return chunked(hours).take(dataPoints).mapIndexed { index, chunk ->
        val startTime = chunk.first().time
        val endTime = chunk.last().time
        
        AggregatedWeatherData(
            startTime = startTime,
            endTime = endTime,
            periodLabel = startTime.hour.toString().padStart(2, '0'),
            temperature = WeatherStatData(
                min = chunk.minOf { it.temperature },
                max = chunk.maxOf { it.temperature }
            ),
            feelsLike = chunk.firstNotNullOfOrNull { it.feelsLike }?.let { feelsLike ->
                WeatherStatData(
                    min = chunk.mapNotNull { it.feelsLike }.minOrNull() ?: feelsLike,
                    max = chunk.mapNotNull { it.feelsLike }.maxOrNull() ?: feelsLike
                )
            },
            precipitation = WeatherStatData(
                min = chunk.minOf { it.precipitation },
                max = chunk.maxOf { it.precipitation },
                total = chunk.sumOf { it.precipitation.toDouble() }.toFloat()
            ),
            windSpeed = WeatherStatData(
                min = chunk.minOf { it.windSpeed },
                max = chunk.maxOf { it.windSpeed }
            ),
            windGust = chunk.firstNotNullOfOrNull { it.windGust }?.let { gust ->
                WeatherStatData(
                    min = chunk.mapNotNull { it.windGust }.minOrNull() ?: gust,
                    max = chunk.mapNotNull { it.windGust }.maxOrNull() ?: gust
                )
            },
            humidity = chunk.firstNotNullOfOrNull { it.humidity }?.let { humidity ->
                WeatherStatData(
                    min = chunk.mapNotNull { it.humidity }.minOrNull() ?: humidity,
                    max = chunk.mapNotNull { it.humidity }.maxOrNull() ?: humidity
                )
            },
            dewPoint = chunk.firstNotNullOfOrNull { it.dewPoint }?.let { dewPoint ->
                WeatherStatData(
                    min = chunk.mapNotNull { it.dewPoint }.minOrNull() ?: dewPoint,
                    max = chunk.mapNotNull { it.dewPoint }.maxOrNull() ?: dewPoint
                )
            },
            visibility = chunk.firstNotNullOfOrNull { it.visibility }?.let { visibility ->
                WeatherStatData(
                    min = chunk.mapNotNull { it.visibility }.minOrNull() ?: visibility,
                    max = chunk.mapNotNull { it.visibility }.maxOrNull() ?: visibility
                )
            },
            cloudCover = chunk.firstNotNullOfOrNull { it.cloudCover }?.let { cloudCover ->
                WeatherStatData(
                    min = chunk.mapNotNull { it.cloudCover }.minOrNull() ?: cloudCover,
                    max = chunk.mapNotNull { it.cloudCover }.maxOrNull() ?: cloudCover
                )
            },
            cloudBase = chunk.firstNotNullOfOrNull { it.cloudBase }?.let { cloudBase ->
                WeatherStatData(
                    min = chunk.mapNotNull { it.cloudBase }.minOrNull() ?: cloudBase,
                    max = chunk.mapNotNull { it.cloudBase }.maxOrNull() ?: cloudBase
                )
            },
            precipitationChance = chunk.firstNotNullOfOrNull { it.precipitationChance }?.let { chance ->
                WeatherStatData(
                    min = chunk.mapNotNull { it.precipitationChance }.minOrNull() ?: chance,
                    max = chunk.mapNotNull { it.precipitationChance }.maxOrNull() ?: chance
                )
            },
            snow = chunk.firstNotNullOfOrNull { it.snow }?.let { snow ->
                WeatherStatData(
                    min = chunk.mapNotNull { it.snow }.minOrNull() ?: snow,
                    max = chunk.mapNotNull { it.snow }.maxOrNull() ?: snow
                )
            },
            ice = chunk.firstNotNullOfOrNull { it.ice }?.let { ice ->
                WeatherStatData(
                    min = chunk.mapNotNull { it.ice }.minOrNull() ?: ice,
                    max = chunk.mapNotNull { it.ice }.maxOrNull() ?: ice
                )
            },
            dominantCondition = chunk.groupBy { it.condition }
                .maxByOrNull { it.value.size }?.key ?: WeatherCondition.Unknown
        )
    }
}

private fun List<WeatherChartPoint>.aggregateDaily(): List<AggregatedWeatherData> {
    return groupBy { it.time.toLocalDate() }
        .entries
        .take(7)
        .map { (date, points) ->
            AggregatedWeatherData(
                startTime = points.first().time,
                endTime = points.last().time,
                periodLabel = date.dayOfWeek.toString().take(3),
                temperature = WeatherStatData(
                    min = points.minOf { it.temperature },
                    max = points.maxOf { it.temperature }
                ),
                feelsLike = points.firstNotNullOfOrNull { it.feelsLike }?.let { feelsLike ->
                    WeatherStatData(
                        min = points.mapNotNull { it.feelsLike }.minOrNull() ?: feelsLike,
                        max = points.mapNotNull { it.feelsLike }.maxOrNull() ?: feelsLike
                    )
                },
                precipitation = WeatherStatData(
                    min = points.minOf { it.precipitation },
                    max = points.maxOf { it.precipitation },
                    total = points.sumOf { it.precipitation.toDouble() }.toFloat()
                ),
                windSpeed = WeatherStatData(
                    min = points.minOf { it.windSpeed },
                    max = points.maxOf { it.windSpeed }
                ),
                windGust = points.firstNotNullOfOrNull { it.windGust }?.let { gust ->
                    WeatherStatData(
                        min = points.mapNotNull { it.windGust }.minOrNull() ?: gust,
                        max = points.mapNotNull { it.windGust }.maxOrNull() ?: gust
                    )
                },
                humidity = points.firstNotNullOfOrNull { it.humidity }?.let { humidity ->
                    WeatherStatData(
                        min = points.mapNotNull { it.humidity }.minOrNull() ?: humidity,
                        max = points.mapNotNull { it.humidity }.maxOrNull() ?: humidity
                    )
                },
                dewPoint = points.firstNotNullOfOrNull { it.dewPoint }?.let { dewPoint ->
                    WeatherStatData(
                        min = points.mapNotNull { it.dewPoint }.minOrNull() ?: dewPoint,
                        max = points.mapNotNull { it.dewPoint }.maxOrNull() ?: dewPoint
                    )
                },
                visibility = points.firstNotNullOfOrNull { it.visibility }?.let { visibility ->
                    WeatherStatData(
                        min = points.mapNotNull { it.visibility }.minOrNull() ?: visibility,
                        max = points.mapNotNull { it.visibility }.maxOrNull() ?: visibility
                    )
                },
                cloudCover = points.firstNotNullOfOrNull { it.cloudCover }?.let { cloudCover ->
                    WeatherStatData(
                        min = points.mapNotNull { it.cloudCover }.minOrNull() ?: cloudCover,
                        max = points.mapNotNull { it.cloudCover }.maxOrNull() ?: cloudCover
                    )
                },
                cloudBase = points.firstNotNullOfOrNull { it.cloudBase }?.let { cloudBase ->
                    WeatherStatData(
                        min = points.mapNotNull { it.cloudBase }.minOrNull() ?: cloudBase,
                        max = points.mapNotNull { it.cloudBase }.maxOrNull() ?: cloudBase
                    )
                },
                precipitationChance = points.firstNotNullOfOrNull { it.precipitationChance }?.let { chance ->
                    WeatherStatData(
                        min = points.mapNotNull { it.precipitationChance }.minOrNull() ?: chance,
                        max = points.mapNotNull { it.precipitationChance }.maxOrNull() ?: chance
                    )
                },
                snow = points.firstNotNullOfOrNull { it.snow }?.let { snow ->
                    WeatherStatData(
                        min = points.mapNotNull { it.snow }.minOrNull() ?: snow,
                        max = points.mapNotNull { it.snow }.maxOrNull() ?: snow
                    )
                },
                ice = points.firstNotNullOfOrNull { it.ice }?.let { ice ->
                    WeatherStatData(
                        min = points.mapNotNull { it.ice }.minOrNull() ?: ice,
                        max = points.mapNotNull { it.ice }.maxOrNull() ?: ice
                    )
                },
                dominantCondition = points.groupBy { it.condition }
                    .maxByOrNull { it.value.size }?.key ?: WeatherCondition.Unknown
            )
        }
}


/**
 * Convert daily forecast points to chart data
 */
fun List<DailyForecastPoint>.toDailyForecastChartData(): DailyForecastChartData {
    if (isEmpty()) {
        return DailyForecastChartData(
            dataPoints = emptyList(),
            availableStats = emptySet(),
            startDate = LocalDate.now(),
            endDate = LocalDate.now()
        )
    }
    
    val dataPoints = map { point ->
        AggregatedDailyWeatherData(
            date = point.date,
            dayName = point.dayName,
            dayNameShort = point.dayNameShort,
            temperature = DailyWeatherStatData(
                min = point.tempMin.toFloat(),
                max = point.tempMax.toFloat()
            ),
            feelsLike = point.feelsLike?.let { DailyWeatherStatData(it.toFloat(), it.toFloat()) },
            precipitation = DailyWeatherStatData(
                min = point.precipitation.toFloat(),
                max = point.precipitation.toFloat(),
                total = point.precipitation.toFloat()
            ),
            windSpeed = DailyWeatherStatData(
                min = point.windSpeed,
                max = point.windSpeed
            ),
            windGust = point.windGust?.let { DailyWeatherStatData(it, it) },
            humidity = point.humidity?.let { DailyWeatherStatData(it.toFloat(), it.toFloat()) },
            dewPoint = point.dewPoint?.let { DailyWeatherStatData(it.toFloat(), it.toFloat()) },
            visibility = point.visibility?.let { DailyWeatherStatData(it.toFloat(), it.toFloat()) },
            cloudCover = point.cloudCover?.let { DailyWeatherStatData(it.toFloat(), it.toFloat()) },
            cloudBase = point.cloudBase?.let { DailyWeatherStatData(it.toFloat(), it.toFloat()) },
            precipitationChance = DailyWeatherStatData(
                min = point.precipitationChance.toFloat(),
                max = point.precipitationChance.toFloat()
            ),
            snow = point.snow?.let { DailyWeatherStatData(it, it) },
            ice = point.ice?.let { DailyWeatherStatData(it, it) },
            dominantCondition = point.condition,
            isNight = point.isNight,
            sunriseTime = point.sunriseTime,
            sunsetTime = point.sunsetTime
        )
    }
    
    val availableStats = mutableSetOf<WeatherStatistic>().apply {
        add(TEMPERATURE)
        add(PRECIPITATION)
        add(PRECIPITATION_CHANCE)
        add(WIND_SPEED)
        if (dataPoints.any { it.feelsLike != null }) add(FEELS_LIKE)
        if (dataPoints.any { it.windGust != null }) add(WIND_GUST)
        if (dataPoints.any { it.humidity != null }) add(HUMIDITY)
        if (dataPoints.any { it.dewPoint != null }) add(DEW_POINT)
        if (dataPoints.any { it.visibility != null }) add(VISIBILITY)
        if (dataPoints.any { it.cloudCover != null }) add(CLOUD_COVER)
        if (dataPoints.any { it.cloudBase != null }) add(CLOUD_BASE)
        if (dataPoints.any { it.snow != null }) add(SNOW)
        if (dataPoints.any { it.ice != null }) add(ICE)
    }
    
    return DailyForecastChartData(
        dataPoints = dataPoints,
        availableStats = availableStats,
        startDate = dataPoints.first().date,
        endDate = dataPoints.last().date
    )
}