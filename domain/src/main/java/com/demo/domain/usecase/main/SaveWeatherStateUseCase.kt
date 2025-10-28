package com.demo.domain.usecase.main

import com.demo.domain.entity.common.Coordinates
import com.demo.domain.entity.main.WeatherState
import com.demo.domain.entity.repository.HomeRepository
import com.demo.domain.preferences.AppPreferences
import javax.inject.Inject

class SaveWeatherStateUseCase @Inject constructor(
    private val homeRepository: HomeRepository,
    private val appPreferences: AppPreferences
) {

    suspend operator fun invoke(state: WeatherState, coordinates: Coordinates) {
        homeRepository.saveMainState(state, coordinates)
        appPreferences.setLastUpdateTime(System.currentTimeMillis())
    }

}