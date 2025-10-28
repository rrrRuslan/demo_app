package com.demo.domain.entity.forecast

enum class ForecastType(val value: Int, val maxTime: String) {
    DAILY(1, "7d"),
    HOURLY(0, "8d")
}