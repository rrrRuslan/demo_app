package com.demo.data.remote.entity

import com.google.gson.annotations.SerializedName

data class ForecastDtoItem(
    @SerializedName("date")
    val date: ForecastDateDto?,
    @SerializedName("day_name")
    val day_name: Any?,
    @SerializedName("day_name_short")
    val day_name_short: String?,
    @SerializedName("iceaccum")
    val iceaccum: Any?,
    @SerializedName("localtime")
    val localtime: String?,
    @SerializedName("feelslike")
    val feelsLike: Int?,
    @SerializedName("maxt")
    val maxt: Any?,
    @SerializedName("mint")
    val mint: Any?,
    @SerializedName("night")
    val night: Boolean?,
    @SerializedName("pop12")
    val pop12: Int?,
    @SerializedName("qpf")
    val qpf: Any?,
    @SerializedName("rhm")
    val rhm: Any?,
    @SerializedName("sky")
    val sky: Any?,
    @SerializedName("snow")
    val snow: Any?,
    @SerializedName("sunrise_t")
    val sunrise_t: Any?,
    @SerializedName("sunset_t")
    val sunset_t: Any?,
    @SerializedName("td")
    val td: Any?,
    @SerializedName("temp")
    val temp: Int?,
    @SerializedName("hi")
    val hi: Int?,
    @SerializedName("lo")
    val lo: Int?,
    @SerializedName("validt")
    val validt: Long?,
    @SerializedName("wdir")
    val wdir: Float?,
    @SerializedName("wdir_compass")
    val wdir_compass: String?,
    @SerializedName("wgust")
    val wgust: Float?,
    @SerializedName("wspd")
    val wspd: Float?,
    @SerializedName("wx")
    val wx: String?,
    @SerializedName("wx_icon")
    val wx_icon: String?,
    @SerializedName("wx_str")
    val wx_str: String?,
)