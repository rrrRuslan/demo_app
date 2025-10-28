package com.demo.presentation.common

import androidx.compose.runtime.Immutable
import com.demo.domain.entity.weather.WeatherCondition
import com.demo.presentation.R

object WeatherAnimationManager {

    fun WeatherCondition.getWeatherAnimation(isNight: Boolean): WeatherAnimation = when {
        isFreezingRain -> if (isNight) WeatherAnimation.RainNight else WeatherAnimation.FreezingRainDay
        isRain -> if (isNight) WeatherAnimation.RainNight else WeatherAnimation.RainDay
        isSnow -> if (isNight) WeatherAnimation.SnowNight else WeatherAnimation.SnowDay
        isOvercast -> if (!isNight) WeatherAnimation.OvercastDay else WeatherAnimation.MostlyCloudyNight
        isMostlyCloudy -> if (isNight) WeatherAnimation.MostlyCloudyNight else WeatherAnimation.MostlyCloudyDay
        isPartlyCloudy -> if (isNight) WeatherAnimation.CloudyNight else WeatherAnimation.PartlyCloudyDay
        else -> if (isNight) WeatherAnimation.ClearNight else WeatherAnimation.ClearDay
    }

}

@Immutable
enum class AnimationSize {
    Small,
    Medium,
    Large
}

@Immutable
enum class WeatherAnimation {
    ClearDay,
    ClearNight,
    CloudyNight,
    PartlyCloudyDay,
    MostlyCloudyDay,
    MostlyCloudyNight,
    OvercastDay,
    FreezingRainDay,
    RainDay,
    RainNight,
    SnowDay,
    SnowNight,
    Unknown;

    private val widgetRes: Int
        get() = when (this) {
            Unknown -> R.raw.clear_day_small
            ClearDay -> R.raw.clear_day_small
            ClearNight -> R.raw.clear_night_small
            CloudyNight -> R.raw.cloudy_night_small
            PartlyCloudyDay -> R.raw.partly_cloudy_day_small
            MostlyCloudyDay -> R.raw.mostly_cloudy_day_small
            MostlyCloudyNight -> R.raw.mostly_cloudy_night_small
            OvercastDay -> R.raw.mostly_cloudy_day_small
            RainDay -> R.raw.rain_day_small
            RainNight -> R.raw.rain_night_small
            SnowDay -> R.raw.snow_day_small
            SnowNight -> R.raw.snow_night_small
            FreezingRainDay -> R.raw.freezing_rain_small
        }

    private val fullRes: Int
        get() = when (this) {
            Unknown -> R.raw.clear_day
            ClearDay -> R.raw.clear_day
            ClearNight -> R.raw.clear_night
            CloudyNight -> R.raw.cloudy_night
            PartlyCloudyDay -> R.raw.partly_cloudy_day
            MostlyCloudyDay -> R.raw.mostly_cloudy_day
            MostlyCloudyNight -> R.raw.cloudy_night
            OvercastDay -> R.raw.overcast_day
            RainDay -> R.raw.rain_day
            RainNight -> R.raw.rain_night
            SnowDay -> R.raw.snow_day
            SnowNight -> R.raw.snow_night
            FreezingRainDay -> R.raw.freezing_rain
        }

    fun getAnimationRes(animationSize: AnimationSize) = when (animationSize) {
        AnimationSize.Small -> widgetRes
        AnimationSize.Medium -> fullRes
        AnimationSize.Large -> fullRes
    }
}