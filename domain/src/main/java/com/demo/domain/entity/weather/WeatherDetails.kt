package com.demo.domain.entity.weather

data class WeatherDetails(
    val dewpoint: Int?,
    val humidity: String?,
    val windChill: Int?,
    val heatIndex: Int?,
    val winds: WeatherWinds,
    val pressure: Float?,
    val condition: WeatherCondition,
    val precip: Float?,
)