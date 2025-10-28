package com.demo.domain.entity.weather

data class WeatherInfo(
    val temperature: Int?,
    val condition: WeatherCondition,
    val description: String,
    val feelsLike: Int?,
)