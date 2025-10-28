package com.demo.domain.usecase.main

import com.demo.domain.entity.alerts.Alert
import com.demo.domain.entity.common.WeatherTheme
import com.demo.domain.entity.forecast.DailyForecast
import com.demo.domain.entity.forecast.DailyForecastReport
import com.demo.domain.entity.forecast.HourlyForecast
import com.demo.domain.entity.forecast.HourlyReport
import com.demo.domain.entity.main.WeatherState
import com.demo.domain.entity.place.Place
import com.demo.domain.entity.skyCams.SkyCams
import com.demo.domain.entity.weather.Weather
import com.demo.domain.preferences.AppPreferences
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.first
import javax.inject.Inject

const val UPDATE_DELAY_TIME = 900000L // 15 minutes

enum class GetWeatherStateUseCaseState {
    OPTIONAL,
    FORCED,
    CACHED
}

class GetWeatherStateUseCase @Inject constructor(
    private val appPreferences: AppPreferences,
    private val settingsPreferences: SettingsPreferences,
    private val testPreferences: TestPreferences,
    private val getForecastUseCase: GetForecastUseCase,
    private val getHourlyForecastUseCase: GetHourlyForecastUseCase,
    private val getDailyForecastUseCase: GetDailyForecastUseCase,
    private val getAlertsUseCase: GetAlertsUseCase,
    private val getWeatherUseCase: GetWeatherUseCase,
    private val getSkyCamsUseCase: GetSkyCamsUseCase,
    private val getHourlyReportStateUseCase: GetHourlyReportStateUseCase,
    private val getDailyForecastReportUseCase: GetDailyForecastReportUseCase,
    private val saveWeatherStateUseCase: SaveWeatherStateUseCase,
    private val getSavedWeatherStateUseCase: GetSavedWeatherStateUseCase,
    private val updateAlertIsActiveUseCase: UpdateAlertIsActiveUseCase,
    private val getCurrentPlaceUseCase: GetCurrentPlaceUseCase,
    private val userRepository: UserRepository,
) {

    private suspend fun currentCoordinates() = appPreferences.coordinates.first()
    private suspend fun currentUnites() = settingsPreferences.units.first()

    suspend operator fun invoke(state: GetWeatherStateUseCaseState): WeatherState? {

        when (state) {
            GetWeatherStateUseCaseState.FORCED -> Unit
            GetWeatherStateUseCaseState.CACHED -> return getSavedWeatherStateUseCase()?.first?.copy(weatherLiveUrl = "")
            GetWeatherStateUseCaseState.OPTIONAL -> {
                getSavedWeatherStateUseCase()?.let { cachedState ->
                    val currentTime = System.currentTimeMillis()
                    val updateTime = appPreferences.lastUpdateTime.first() + UPDATE_DELAY_TIME
                    Timber.d("Current coordinates: " + currentCoordinates() + "; cached coordinates: " + cachedState.second)
                    when {
                        currentCoordinates().toString() != cachedState.second.toString() -> Unit
                        currentUnites() != cachedState.first.units -> Unit
                        updateTime < currentTime -> {
                            Timber.d("Data is outdated: now: $currentTime, last update with delay: $updateTime\ndifference: ${currentTime - updateTime}")
                        }

                        else -> {
                            Timber.d("Restored from cache")
                            return cachedState.first
                        }
                    }
                }
            }
        }

        val place = getCurrentPlaceUseCase()
        val coordinates = if (!appPreferences.isInMyLocation()) {
            place.coordinates
        } else {
            currentCoordinates()
        }
        var weather: Weather? = null
        var alerts: List<Alert>? = null
        var hourlyForecast: List<HourlyForecast>? = null
        var dailyForecast: List<DailyForecast>? = null
        var skyCams: List<SkyCams>? = null
        var hourlyReports: List<HourlyReport>? = null
        var dailyForecastReports: List<DailyForecastReport>? = null

        appPreferences.setTimeZoneId(place.zoneId)

        coroutineScope {
            listOf(
                async { weather = getWeatherUseCase(GetWeather.ByStation(coordinates)) },
                async { alerts = getAlertsUseCase(GetAlertsUseCaseType.ByCoordinates(coordinates)) },
                async { hourlyForecast = getHourlyForecastUseCase(coordinates, currentUnites()) },
                async { dailyForecast = getDailyForecastUseCase(coordinates, currentUnites()) },
                async { skyCams = getSkyCamsUseCase(coordinates) },
                async { hourlyReports = getHourlyReportStateUseCase()?.hourlyForecast },
                async { dailyForecastReports = getDailyForecastReportUseCase(7) },
            ).awaitAll()
        }

        updateAlertIsActiveUseCase(alerts ?: emptyList())

        val theme = if (testPreferences.overrideTheme.first()) {
            appPreferences.appTheme.first()
        } else {
            weather?.theme?.let { appPreferences.setAppTheme(it) }
            weather?.theme
        }

        val weatherState = when {
            weather == null || weather?.info?.temperature == null || weather?.station?.id == null || weather?.station?.name == null -> {
                if (shouldUpdate(alerts, hourlyForecast, dailyForecast)) {
                    getStateWithoutWeather(alerts, hourlyForecast, dailyForecast, place, theme, skyCams, hourlyReports, dailyForecastReports)
                } else {
                    getSavedWeatherStateUseCase()?.first?.copy(weatherLiveUrl = "")
                }
            }

            else -> WeatherState(
                weather = weather,
                alerts = alerts,
                forecast = getForecastUseCase(dailyForecast, hourlyForecast),
                weatherLiveUrl = LiveMapData.getUrl(currentCoordinates(), userRepository.getDestinationId()),
                currentPlace = place,
                units = currentUnites(),
                theme = theme,
                skyCams = skyCams,
                zoneId = appPreferences.timeZoneId.first(),
                hourlyReports = hourlyReports,
                dailyForecastReports = dailyForecastReports
            )
        }

        weatherState?.let {
            try {
                saveWeatherStateUseCase(it, currentCoordinates())
            } catch (e: Exception) {
                Timber.e(e, "Error saving weather state")
            }
        }
        return weatherState
    }

    private fun shouldUpdate(
        alerts: List<Alert>?,
        hourlyForecast: List<HourlyForecast>?,
        dailyForecast: List<DailyForecast>?
    ) = alerts != null || hourlyForecast != null || dailyForecast != null

    private suspend fun getStateWithoutWeather(
        alerts: List<Alert>?,
        hourlyForecast: List<HourlyForecast>?,
        dailyForecast: List<DailyForecast>?,
        place: Place,
        theme: WeatherTheme?,
        skyCams: List<SkyCams>?,
        hourlyReports: List<HourlyReport>?,
        dailyForecastReports: List<DailyForecastReport>?
    ): WeatherState {
        val cachedWeather = getSavedWeatherStateUseCase()?.first?.weather
        return WeatherState(
            weather = cachedWeather,
            alerts = alerts,
            forecast = getForecastUseCase(dailyForecast, hourlyForecast),
            weatherLiveUrl = LiveMapData.getUrl(currentCoordinates(), userRepository.getDestinationId()),
            currentPlace = place,
            units = currentUnites(),
            theme = theme,
            skyCams = skyCams,
            zoneId = appPreferences.timeZoneId.first(),
            hourlyReports = hourlyReports,
            dailyForecastReports = dailyForecastReports
        )
    }

}