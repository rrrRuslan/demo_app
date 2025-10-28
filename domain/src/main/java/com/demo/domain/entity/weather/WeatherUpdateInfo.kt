package com.demo.domain.entity.weather

import java.time.ZonedDateTime

data class WeatherUpdateInfo(
    val description: String,
    val time: ZonedDateTime?,
)