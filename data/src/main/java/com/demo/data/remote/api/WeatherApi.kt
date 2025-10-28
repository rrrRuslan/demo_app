package com.demo.data.remote.api

import com.demo.data.remote.entity.ForecastDto
import com.weatherusa.data.remote.entity.alerts.UserAlertsDto
import com.weatherusa.data.remote.entity.alerts.info.AlertInfoDto
import com.weatherusa.data.remote.entity.device.AddDeviceDto
import com.weatherusa.data.remote.entity.device.UpdateDeviceDto
import com.weatherusa.data.remote.entity.forecast.ForecastDto
import com.weatherusa.data.remote.entity.forecast.v1_3.ForecastV1_3Dto
import com.weatherusa.data.remote.entity.observation.WeatherObservationDto
import com.weatherusa.data.remote.entity.skyCams.SkyCamsDto
import com.weatherusa.data.remote.entity.station.StationListDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface WeatherApi {

    @GET("forecast")
    suspend fun getForecast(
        @Query("q") coordinates: String,
        @Query("daily") daily: Int,
        @Query("units") units: String,
        @Query("maxtime") maxtime: String,
    ): Response<ForecastDto?>

    @GET("forecast")
    suspend fun getForecastV1_3(
        @Query("q") coordinates: String,
        @Query("daily") daily: Int,
        @Query("units") units: String,
        @Query("maxtime") maxtime: String,
    ): Response<ForecastDto?>

    @GET("place")
    suspend fun searchPlace(
        @Query("q") query: String,
    ): Response<Any?>

    @GET("alerts")
    suspend fun getAlerts(
        @Query("q") query: String? = null,
        @Query("fips") fips: String? = null,
        @Query("wxzone") wxzone: String? = null,
        @Query("fire_zone") fire_zone: String? = null,
    ): Response<Any?>


    @GET("user")
    suspend fun addDevice(
        @Query("token") token: String,
        @Query("version_code") versionCode: Int,
        @Query("device_name") deviceName: String,
        @Query("mode") mode: String = "add_device",
        @Query("platform") platform: String = "android"
    ): Response<AddDeviceDto?>

    @GET("user")
    suspend fun updateDevice(
        @Query("destination_id") destinationId: String,
        @Query("token") token: String,
        @Query("previous_token") previousToken: String,
        @Query("mode") mode: String = "update_device",
    ): Response<UpdateDeviceDto?>

    @POST("user")
    suspend fun updateLocation(
        @Query("device_id") deviceId: String,
        @Query("device_name") deviceName: String,
        @Query("version_code") versionCode: String,
        @Query("q") coordinates: String,
        @Query("mode") mode: String = "update_location",
    ): Response<Unit?>

}