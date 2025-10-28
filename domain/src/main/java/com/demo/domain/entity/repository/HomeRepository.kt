package com.demo.domain.entity.repository

import com.demo.domain.entity.common.Coordinates
import com.demo.domain.entity.main.WeatherState

interface HomeRepository {
    suspend fun saveMainState(weatherState: WeatherState, coordinates: Coordinates)
    suspend fun getMainState(): Pair<WeatherState, Coordinates>?
}