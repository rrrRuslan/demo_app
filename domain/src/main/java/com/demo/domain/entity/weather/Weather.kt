package com.demo.domain.entity.weather

import com.demo.domain.entity.common.WeatherTheme
import com.demo.domain.entity.station.Station

data class Weather(
    val info: WeatherInfo,
    val updateInfo: WeatherUpdateInfo,
    val details: WeatherDetails?,
    val station: Station,
    val theme: WeatherTheme,
) {
    override fun toString(): String {
        return "Temp: ${info.temperature}, feels like: ${info.feelsLike}, ${updateInfo.description}"
    }
}