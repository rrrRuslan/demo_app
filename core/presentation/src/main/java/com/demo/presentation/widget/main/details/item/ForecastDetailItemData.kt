package com.demo.presentation.widget.main.details.item

import com.demo.presentation.R


sealed class ForecastDetailItemData(
    open val value: String,
) {

    data class Dewpoint(override val value: String) : ForecastDetailItemData(value)
    data class Humidity(override val value: String) : ForecastDetailItemData(value)
    data class WindChill(override val value: String) : ForecastDetailItemData(value)
    data class HeatIndex(override val value: String) : ForecastDetailItemData(value)
    data class Winds(override val value: String) : ForecastDetailItemData(value)
    data class Pressure(override val value: String) : ForecastDetailItemData(value)
    data class Precip(override val value: String) : ForecastDetailItemData(value)

    val title: Int
        get() = when (this) {
            is Dewpoint -> R.string.dewpoint
            is HeatIndex -> R.string.heat_index
            is Humidity -> R.string.humidity
            is Precip -> R.string.precip
            is Pressure -> R.string.pressure
            is WindChill -> R.string.wind_chill
            is Winds -> R.string.winds
        }

    val icon: Int
        get() = when (this) {
            is Dewpoint -> R.drawable.ic_dewpoint
            is HeatIndex -> R.drawable.ic_heat_index
            is Humidity -> R.drawable.ic_humidity
            is Precip -> R.drawable.ic_precip
            is Pressure -> R.drawable.ic_pressure
            is WindChill -> R.drawable.ic_wind_chill
            is Winds -> R.drawable.ic_winds
        }

}