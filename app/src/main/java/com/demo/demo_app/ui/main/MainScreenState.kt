package com.demo.demo_app.ui.main

import androidx.compose.runtime.Immutable
import com.demo.domain.entity.common.Units
import com.demo.domain.entity.forecast.DailyForecastReport
import com.demo.presentation.common.WeatherAnimation
import com.demo.presentation.widget.main.alerts.AlertItemData
import com.demo.presentation.widget.main.details.ForecastDetailsItemData
import com.demo.presentation.widget.main.forecast.daily.ForecastDailyItemData
import com.demo.presentation.widget.main.forecast.hourly.ForecastHourlyItemData
import com.demo.presentation.widget.main.skyCams.data.SkyCamsData
import com.demo.presentation.widget.main.today.TodayForecastData
import com.demo.presentation.widget.topBar.data.TopBarData
import kotlinx.collections.immutable.ImmutableList
import com.demo.domain.entity.forecast.HourlyReport
import com.demo.domain.entity.place.Place

@Immutable
data class MainScreenState(
    val topBarData: TopBarData.Place,
    val todayForecastData: TodayForecastData,
    val forecastDetails: ForecastDetailsItemData?,
    val alertList: ImmutableList<AlertItemData>,
    val hourlyList: ImmutableList<ForecastHourlyItemData>,
    val dailyList: ImmutableList<ForecastDailyItemData>,
    val weatherLiveUrl: String,
    val skyCamsStreamAvailable: Boolean = false,
    val skyCamsData: SkyCamsData?,
    val hourlyReports: List<HourlyReport>,
    val dailyForecastReports: List<DailyForecastReport>,
    val weatherAnimation: WeatherAnimation,
    val place: Place?,
    val adsEnabled: Boolean = false,
    val starState: StarState = StarState.Hidden,
    val isConnectionAvailable: Boolean = true,
    val isRefreshing: Boolean = false,
    val currentUnits: Units = Units.English
)

@Immutable
enum class StarState {
    Active,
    Inactive,
    Hidden
}