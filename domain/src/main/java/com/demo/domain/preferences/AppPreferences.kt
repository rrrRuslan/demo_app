package com.demo.domain.preferences

import com.demo.domain.entity.common.Coordinates
import com.demo.domain.entity.common.WeatherTheme
import kotlinx.coroutines.flow.Flow
import java.time.ZoneId

interface AppPreferences {

    suspend fun setMyLocationCoordinates(coordinates: Coordinates)
    val myLocationCoordinates: Flow<Coordinates?>

    suspend fun setCoordinatesByGps(isActive: Boolean)
    val coordinatesByGps: Flow<Boolean>

    suspend fun isInMyLocation(): Boolean

    suspend fun setLocationAllowed(isAllowed: Boolean)
    val isLocationAllowed: Flow<Boolean>

    suspend fun setOnboardingCompleted(isCompleted: Boolean)
    val isOnboardingCompleted: Flow<Boolean>

    suspend fun setAlertsIsActive(isActive: Boolean)
    val alertsIsActiveState: Flow<Boolean>

    suspend fun setTimeZoneId(string: String)
    val timeZoneId: Flow<ZoneId>

    suspend fun setAppTheme(theme: WeatherTheme)
    val appTheme: Flow<WeatherTheme>

    suspend fun setCoordinates(coordinates: Coordinates)
    val coordinates: Flow<Coordinates>

    suspend fun setLastUpdateTime(time: Long)
    val lastUpdateTime: Flow<Long>

}