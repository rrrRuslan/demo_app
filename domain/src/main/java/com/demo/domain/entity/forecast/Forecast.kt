package com.demo.domain.entity.forecast

data class Forecast(
    val daily: List<DailyForecast>,
    val hourly: List<HourlyForecast>,
)