package com.demo.data.remote.repository

import android.util.Log
import com.demo.domain.entity.repository.HomeRepository
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(
    private val homeDao: HomeDao,
) : HomeRepository {

    override suspend fun saveMainState(weatherState: WeatherState, coordinates: Coordinates) {
        clearTables()
        Log.d("HomeRepositoryImpl", "Saved to cache place coordinates: ${weatherState.currentPlace?.coordinates.toString()}")
        val id = homeDao.saveMainState(weatherState.mapToStateEntity(coordinates))
        weatherState.currentPlace?.let { homeDao.savePlace(it.toPlaceEntity(id)) }
        weatherState.alerts?.forEach { alert ->
            homeDao.saveAlert(alert.toAlertEntity(id.toInt()))
        }
        weatherState.forecast.hourly.forEach { hourly ->
            homeDao.saveHourly(hourly.toHourlyEntity(stateId = id.toInt()))
        }
        weatherState.forecast.daily.forEach { daily ->
            val dailyId = homeDao.saveDaily(daily.toDailyEntity(id.toInt()))
            daily.hourlyList.forEach { homeDao.saveHourly(it.toHourlyEntity(dailyId = dailyId.toInt())) }
        }
        weatherState.hourlyReports?.forEach { hourlyReport ->
            homeDao.saveHourlyReport(hourlyReport.toHourlyReportEntity(id.toInt()))
        }
        weatherState.dailyForecastReports?.forEach { dailyForecastReport ->
            homeDao.saveDailyForecastReport(dailyForecastReport.toDailyForecastReportEntity(id.toInt()))
        }
        weatherState.skyCams?.forEach { skyCam ->
            homeDao.saveSkyCams(skyCam.toSkyCamEntity(id.toInt()))
        }
    }

    private suspend fun clearTables() {
        with(homeDao) {
            clearState()
            clearDaily()
            clearAlerts()
            clearHourly()
            clearHourlyReports()
            clearDailyForecastReports()
            clearPlace()
            clearSkyCams()
        }
    }

    override suspend fun getMainState(): Pair<WeatherState, Coordinates>? {
        val weather = homeDao.getState().firstOrNull() ?: return null
        val state = weather.mapToWeatherState()
        val coordinates = Coordinates.fromString(weather.weatherState.currentCoordinates)
        Log.d("HomeRepositoryImpl", "Restored from cache place coordinates: ${state.currentPlace?.coordinates.toString()}")
        return Pair(state, coordinates)
    }

}