package com.demo.domain.entity.common

enum class WeatherTheme {
    Day,
    Night;

    val isNight: Boolean
        get() = this == Night

    companion object {
        fun getValueOf(string: String): WeatherTheme {
            return when (string) {
                "Day" -> Day
                "Night" -> Night
                else -> Day
            }
        }
    }

}