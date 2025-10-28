package com.demo.domain.entity.main

import com.demo.domain.entity.alerts.Alert
import com.demo.domain.entity.common.Units
import com.demo.domain.entity.common.WeatherTheme
import com.demo.domain.entity.forecast.DailyForecastReport
import com.demo.domain.entity.forecast.Forecast
import com.demo.domain.entity.forecast.HourlyReport
import com.demo.domain.entity.place.Place
import com.demo.domain.entity.skyCams.SkyCams
import com.demo.domain.entity.weather.Weather
import java.time.ZoneId

data class WeatherState(
    val weather: Weather?,
    val alerts: List<Alert>?,
    val weatherLiveUrl: String,
    val currentPlace: Place?,
    val forecast: Forecast,
    val units: Units,
    val theme: WeatherTheme?,
    val zoneId: ZoneId?,
    val skyCams: List<SkyCams>?,
    val hourlyReports: List<HourlyReport>?,
    val dailyForecastReports: List<DailyForecastReport>?
) {
    companion object {
        val EMPTY = WeatherState(
            weather = null,
            alerts = null,
            weatherLiveUrl = "",
            currentPlace = null,
            forecast = Forecast(emptyList(), emptyList()),
            units = Units.Metric,
            theme = null,
            zoneId = null,
            skyCams = null,
            hourlyReports = null,
            dailyForecastReports = null
        )
    }
}