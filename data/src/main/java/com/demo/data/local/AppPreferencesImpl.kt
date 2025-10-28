package com.demo.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.demo.domain.entity.common.Coordinates
import com.demo.domain.entity.common.WeatherTheme
import com.demo.domain.preferences.AppPreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import timber.log.Timber
import java.time.ZoneId
import javax.inject.Inject

private const val APP_DATASTORE_NAME = "weather_app_usa_datastore"

class AppPreferencesImpl @Inject constructor(private val context: Context) : AppPreferences {

    companion object {
        private val APP_THEME = stringPreferencesKey("app_theme")
        private val COORDINATES_LAT = floatPreferencesKey("coordinates_lat")
        private val COORDINATES_LON = floatPreferencesKey("coordinates_lon")
        private val MY_LOCATION_LAT = floatPreferencesKey("my_location_lat")
        private val MY_LOCATION_LON = floatPreferencesKey("my_location_lon")
        private val TIME_ZONE_ID = stringPreferencesKey("time_zone_id")
        private val ONBOARDING_COMPLETED = booleanPreferencesKey("is_onboarding_completed")
        private val ALERT_ACTIVE = booleanPreferencesKey("is_alerts_active")
        private val LOCATION_ALLOWED = booleanPreferencesKey("is_location_allowed")
        private val COORDINATES_BY_GPS = booleanPreferencesKey("coordinates_by_gps")
        private val LAST_UPDATE_TIME = longPreferencesKey("last_update_time")

        private val defaultTimeZone by lazy {
            val americaTimeZone = "America/New_York"
            val utcTimeZone = "UTC"
            try {
                ZoneId.of(americaTimeZone)
            } catch (e: Exception) {
                ZoneId.of(utcTimeZone)
            }
        }
    }

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = APP_DATASTORE_NAME)

    override suspend fun setAppTheme(theme: WeatherTheme) {
        context.dataStore.edit { settings -> settings[APP_THEME] = theme.name }
    }

    override val appTheme: Flow<WeatherTheme>
        get() = context.dataStore.data.map { preferences -> WeatherTheme.getValueOf(preferences[APP_THEME] ?: WeatherTheme.Night.name) }

    override val coordinatesByGps: Flow<Boolean>
        get() = context.dataStore.data.map { preferences ->
            preferences[COORDINATES_BY_GPS] ?: false
        }

    override suspend fun setCoordinatesByGps(isActive: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[COORDINATES_BY_GPS] = isActive
        }
    }

    override suspend fun isInMyLocation() = myLocationCoordinates.first() == coordinates.first()

    override val isOnboardingCompleted: Flow<Boolean>
        get() = context.dataStore.data.map { preferences ->
            preferences[ONBOARDING_COMPLETED] ?: false
        }

    override suspend fun setOnboardingCompleted(isCompleted: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[ONBOARDING_COMPLETED] = isCompleted
        }
    }

    override val alertsIsActiveState: Flow<Boolean>
        get() = context.dataStore.data.map { preferences ->
            preferences[ALERT_ACTIVE] ?: false
        }

    override suspend fun setAlertsIsActive(isActive: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[ALERT_ACTIVE] = isActive
        }
    }

    override val isLocationAllowed: Flow<Boolean>
        get() = context.dataStore.data.map { preferences ->
            preferences[LOCATION_ALLOWED] ?: false
        }

    override suspend fun setLocationAllowed(isAllowed: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[LOCATION_ALLOWED] = isAllowed
        }
    }

    override val coordinates: Flow<Coordinates>
        get() = context.dataStore.data.map { preferences ->
            val lat = preferences[COORDINATES_LAT] ?: 0f
            val lon = preferences[COORDINATES_LON] ?: 0f
            Coordinates(lat, lon)
        }

    override suspend fun setCoordinates(coordinates: Coordinates) {
        Timber.d("Current coordinates saved: $coordinates")
        context.dataStore.edit { preferences ->
            preferences[COORDINATES_LAT] = coordinates.latitude
            preferences[COORDINATES_LON] = coordinates.longitude
        }
    }

    override suspend fun setMyLocationCoordinates(coordinates: Coordinates) {
        context.dataStore.edit { preferences ->
            preferences[MY_LOCATION_LAT] = coordinates.latitude
            preferences[MY_LOCATION_LON] = coordinates.longitude
        }
    }

    override val myLocationCoordinates: Flow<Coordinates?>
        get() = context.dataStore.data.map { preferences ->
            val lat = preferences[MY_LOCATION_LAT]
            val lon = preferences[MY_LOCATION_LON]
            if (lat == null || lon == null) {
                null
            } else {
                Coordinates(lat, lon)
            }
        }

    override suspend fun setTimeZoneId(string: String) {
        context.dataStore.edit { settings -> settings[TIME_ZONE_ID] = string }
    }

    override val timeZoneId: Flow<ZoneId>
        get() = context.dataStore.data.map { preferences ->
            try {
                val id = preferences[TIME_ZONE_ID]
                if (id.isNullOrBlank()) {
                    defaultTimeZone
                } else {
                    try {
                        ZoneId.of(id)
                    } catch (e: Exception) {
                        Timber.e(e.localizedMessage.orEmpty())
                        defaultTimeZone
                    }
                }
            } catch (e: Exception) {
                Timber.e(e.localizedMessage.orEmpty())
                defaultTimeZone
            }
        }

    override suspend fun setLastUpdateTime(time: Long) {
        context.dataStore.edit { settings -> settings[LAST_UPDATE_TIME] = time }
    }

    override val lastUpdateTime: Flow<Long>
        get() = context.dataStore.data.map { preferences -> preferences[LAST_UPDATE_TIME] ?: 0L }

}