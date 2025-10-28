package com.demo.demo_app.ui.mapper

import android.R
import android.content.Context
import com.demo.common.extensions.byPattern
import com.demo.common.extensions.formatTime
import com.demo.common.extensions.isToday
import com.demo.common.extensions.isTomorrow
import com.demo.common.extensions.toZonedDateTime
import com.demo.demo_app.ui.main.MainScreenState
import com.demo.presentation.widget.main.alerts.AlertItemData
import com.demo.presentation.widget.topBar.data.TopBarData
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList
import com.demo.domain.entity.alerts.AlertImpact
import com.demo.domain.entity.common.Units
import com.demo.domain.entity.forecast.HourlyForecast
import com.demo.domain.entity.main.WeatherState
import com.demo.domain.entity.place.Place
import com.demo.domain.entity.skyCams.SkyCams
import com.demo.domain.entity.weather.Weather
import com.demo.presentation.common.IconManager.getBwIcon
import com.demo.presentation.common.IconManager.getColorfulIcon
import com.demo.presentation.common.WeatherAnimation
import com.demo.presentation.common.WeatherAnimationManager.getWeatherAnimation
import com.demo.presentation.widget.main.details.ForecastDetailsItemData
import com.demo.presentation.widget.main.forecast.daily.ForecastDailyItemData
import com.demo.presentation.widget.main.forecast.hourly.ForecastHourlyItemData
import com.demo.presentation.widget.main.mediaContent.data.MediaType
import com.demo.presentation.widget.main.skyCams.data.SkyCamsData
import com.demo.presentation.widget.main.today.TodayForecastData
import java.time.Duration
import java.time.ZoneId
import java.time.ZonedDateTime
import javax.inject.Inject

class MainMapper @Inject constructor(
    @ApplicationContext private val context: Context,
) {

    fun map(state: WeatherState): MainScreenState {

        fun getWinds(weather: Weather?): String {
            val windSpeedText = when (state.units) {
                Units.English -> "mph"
                Units.Metric -> "kph"
            }.uppercase()
            weather?.details?.winds?.let { winds ->
                return when (winds.speed) {
                    null -> "-"
                    0 -> return context.getString(R.string.wind_calm)
                    winds.gust -> return "${winds.speed} $windSpeedText"
                    else -> "${winds.compass ?: ""} " +
                            "${winds.speed ?: ""} " +
                            "$windSpeedText ${if (winds.gust != null) "G ${winds.gust}" else ""}"
                }
            } ?: return "-"
        }

        val hourlyMappedList = mapHourlyList(state.forecast.hourly, state.units, state.zoneId)

        val dailyMappedList = state.forecast.daily
            //don't show forecast after 8pm
            .filter { item ->
                item.hourlyList.first().time.byPattern("HH").toIntOrNull()?.let {
                    it < 20
                } ?: false
            }
            .map { item ->
                val tempMinStr = if (item.tempMin == -9999) "-" else item.tempMin.toString() + state.units.symbol
                val tempMaxStr = if (item.tempMax == -9999) "-" else item.tempMax.toString() + state.units.symbol
                ForecastDailyItemData(
                    day = item.dayName,
                    precipitation = "${item.precipitation}%",
                    tempMin = tempMinStr,
                    tempMax = tempMaxStr,
                    icon = item.condition.getBwIcon(),
                    hourlyList = mapHourlyList(item.hourlyList, state.units, state.zoneId),
                    isExpanded = false,
                )
            }

        val lastTimeUpdate = if (state.weather?.updateInfo?.time != null) {
            try {
                val millis = Duration.between(state.weather?.updateInfo?.time, ZonedDateTime.now(state.zoneId)).toMillis()
                millis.formatTime()
            } catch (e: Exception) {
                ""
            }
        } else {
            ""
        }

        val pressureText = if (state.units == Units.English) {
            "in"
        } else {
            "hpa"
        }

        return MainScreenState(

            topBarData = TopBarData.Place(
                town = state.currentPlace?.location ?: "",
                state = state.currentPlace?.stateAbbr ?: "",
                updatedMinutesAgo = lastTimeUpdate
            ),

            alertList = state.alerts?.map {
                AlertItemData(
                    id = it.id,
                    text = it.name,
                    icon = when (it.impact) {
                        AlertImpact.LOW -> R.drawable.ic_alert_blue
                        AlertImpact.MEDIUM -> R.drawable.ic_alert_orange
                        AlertImpact.HIGH -> R.drawable.ic_alert_red
                    }
                )
            }?.toPersistentList() ?: persistentListOf(),

            hourlyList = hourlyMappedList.toPersistentList(),

            dailyList = dailyMappedList.toPersistentList(),

            weatherLiveUrl = state.weatherLiveUrl,

            todayForecastData = TodayForecastData(
                temp = "${state.weather?.info?.temperature}${state.units.symbol}",
                icon = state.weather?.info?.condition?.getColorfulIcon()
                    ?: R.drawable.stat_notify_error,
                description = state.weather?.info?.description.orEmpty(),
                feelsLike = "${state.weather?.info?.feelsLike}${state.units.symbol}",
                isFeelsLikeVisible = state.weather?.info?.feelsLike != state.weather?.info?.temperature && state.weather?.info?.feelsLike != null
            ),

            forecastDetails = state.weather?.details?.let {
                ForecastDetailsItemData(
                    dewpoint = if (it.dewpoint == null) "-" else "${it.dewpoint}${state.units.mark}",
                    humidity = if (it.humidity == null) "-" else "${it.humidity}",
                    windChill = if (it.windChill == null) "-" else "${it.windChill}${state.units.mark}",
                    heatIndex = if (it.heatIndex == null) "-" else "${it.heatIndex}${state.units.mark}",
                    pressure = if (it.pressure == null) "-" else "${it.pressure} $pressureText",
                    precip = if (it.precip == null) "-" else "${it.precip}",
                    winds = getWinds(state.weather),
                    station = state.weather?.station?.name.toString()
                )
            },

            skyCamsStreamAvailable = state.skyCams?.any { it.stream != null } == true,

            skyCamsData = mapSkyCams(
                skyCams = state.skyCams?.firstOrNull(),
                zoneId = state.zoneId,
                currentPlace = state.currentPlace
            ),

            hourlyReports = state.hourlyReports ?: emptyList(),
            dailyForecastReports = state.dailyForecastReports ?: emptyList(),
            weatherAnimation = state.weather?.info?.condition?.getWeatherAnimation(
                state.theme?.isNight ?: false
            ) ?: WeatherAnimation.Unknown,
            place = state.currentPlace,
            currentUnits = state.units,
        )
    }

    private fun mapHourlyList(list: List<HourlyForecast>, units: Units, zoneId: ZoneId?): List<ForecastHourlyItemData> {
        return list.map { item ->
            val time = item.time.byPattern("MMM d")
            val dateText = when {
                item.time.isToday(zoneId) -> context.getString(R.string.today_hourly_forecast, time)
                item.time.isTomorrow(zoneId) -> context.getString(R.string.tomorrow_hourly_forecast, time)
                else -> time
            }
//            val windAngle = Math.toRadians(item.windDirection.toDouble()).toFloat()
//            Log.d("MainMapper",
//                "windAngle: $windAngle, windDirection: ${item.windDirection}"
//            )
            ForecastHourlyItemData(
                timeLong = item.timeLong,
                date = dateText,
                time = item.time.byPattern("h a"),
                temp = "${item.temp}${units.symbol}",
                windSpeed = item.windSpeed.toString(),
                windAngle = item.windDirection.toFloat(),
                precipitation = "${item.precipitation}%",
                icon = item.condition.getBwIcon()
            )
        }
    }

    private fun mapSkyCams(skyCams: SkyCams?, zoneId: ZoneId?, currentPlace: Place?): SkyCamsData? {
        val imageUrl = skyCams?.imageUrl ?: return null

        val mediaType = MediaType.getSourceType(
            streamUrl = skyCams.stream,
            imageUrl = imageUrl
        )

        if (mediaType == null) return null

        val skyCamTime = zoneId?.let { skyCams.lastUpdateTime?.toZonedDateTime(it) }
        val time = if (skyCamTime != null) {
            try {
                val ago = Duration.between(skyCamTime, ZonedDateTime.now(zoneId)).toMillis()
                "${skyCamTime.byPattern("MM/dd/yyyy hh:mm a")}, ${ago.formatTime()} ago"
            } catch (e: Exception) {
                "Unknown update time"
            }
        } else {
            "Unknown update time"
        }

        return SkyCamsData(
            imageUrl = imageUrl,
            mediaType = mediaType,
            location = skyCams.name ?: "Unknown SkyCam name",
            state = "${skyCams.location}, ${currentPlace?.stateAbbr}",
            lastUpdateTime = skyCams.lastUpdateTime,
            lastUpdateTimeFormatted = time
        )
    }

}