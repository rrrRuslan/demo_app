package com.demo.demo_app.ui.main

import android.R
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.TopEnd
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.demo.common.manager.EventBus
import com.demo.common.manager.RefreshMainScreen
import com.demo.common.utils.State
import com.demo.presentation.widget.main.alerts.AlertItemData
import com.demo.presentation.common.AnimationSize
import com.demo.presentation.common.WeatherAnimation
import com.demo.presentation.widget.button.AppButton
import com.demo.presentation.widget.error.ErrorItem
import com.demo.presentation.widget.main.loader.Loader
import com.demo.presentation.widget.main.details.ForecastDetails
import com.demo.presentation.widget.main.forecast.ForecastHeader
import com.demo.presentation.widget.main.forecast.daily.ForecastDaily
import com.demo.presentation.widget.main.forecast.hourly.ForecastHourly
import com.demo.presentation.widget.main.noDataHolder.NoDataHolder
import com.demo.presentation.widget.main.skyCams.SkyCams
import com.demo.presentation.widget.main.skyCams.SkyCamsHeader
import com.demo.presentation.widget.main.skyCams.data.SkyCamsData
import com.demo.presentation.widget.main.today.TodayForecast
import com.demo.presentation.widget.main.today.TodayForecastData
import com.demo.presentation.widget.main.weatherLive.ForecastWeatherLive
import com.demo.presentation.widget.main.weatherLive.ForecastWeatherLiveHeader
import com.demo.presentation.widget.topBar.TopBar
import com.demo.presentation.widget.topBar.data.TopBarData
import com.demo.presentation.widget.topBar.data.TopBarIcon
import com.demo.theme.Shapes
import kotlinx.collections.immutable.toPersistentList
import kotlinx.collections.immutable.toPersistentSet
import kotlinx.coroutines.launch
import com.demo.demo_app.navigation.NavigationEvent
import com.demo.domain.entity.common.Units
import com.demo.presentation.widget.admob.AdMobBanner
import com.demo.presentation.widget.main.forecast.daily.ForecastDailyItemData
import com.demo.presentation.widget.main.forecast.hourly.ForecastHourlyItemData


@Composable
fun MainScreen(
    navigationEventListener: (event: NavigationEvent) -> Unit,
    viewModel: MainViewModel = hiltViewModel(),
) {

    val context = LocalContext.current

    val screenState by viewModel.mainScreenState.collectAsState()
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(true) {
        coroutineScope.launch {
            EventBus.subscribe<RefreshMainScreen> {
                viewModel.refresh()
            }
        }
    }

    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = (screenState as? State.Successes)?.data?.isRefreshing ?: false)
    SwipeRefresh(
        state = swipeRefreshState,
        onRefresh = { viewModel.refresh() },
        modifier = Modifier.fillMaxSize()
    ) {
        when (val state = screenState) {
            is State.Error -> ErrorItem(
                Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            )

            is State.Loading -> {}
            is State.Successes -> {
                MainScreenContent(
                    state = state.data,
                    onMapExpandClick = { navigationEventListener(NavigationEvent.LiveMap) },
                    onMenu = { navigationEventListener(NavigationEvent.OpenDrawer) },
                    onSearch = { navigationEventListener(NavigationEvent.Search(SearchScreenType.Location)) },
                    onAlertClick = { navigationEventListener(NavigationEvent.Alerts) },
                    onStarClick = { viewModel.changeFavoriteState() },
                    changeAnimation = { viewModel.changeAnimation(it) },
                    onSkyCams = {
                        val lastUpdateTime = it.lastUpdateTime ?: return@MainScreenContent

                        if (state.data.skyCamsStreamAvailable) {
                            navigationEventListener(NavigationEvent.LiveSkyCams)
                        } else {
                            navigationEventListener(
                                NavigationEvent.FullscreenImage(
                                    url = it.mediaType.url,
                                    name = it.location,
                                    state = it.state,
                                    date = lastUpdateTime,
                                )
                            )
                        }

                    },
                    onHourlyForecastClick = {
                        navigationEventListener(NavigationEvent.HourlyReport(it))
                    },
                    onDailyHourlyForecastClick = {
                        navigationEventListener(NavigationEvent.HourlyReport(it, true))
                    },
                                         onChartDetailsClick = {
                         // Store the chart data before navigating
                         ChartDataStore.setData(
                             hourly = state.data.hourlyReports,
                             daily = state.data.dailyForecastReports
                         )
                         
                         navigationEventListener(NavigationEvent.NavigateToChartDetail(
                             locationName = state.data.topBarData.let {
                                 when (it) {
                                     else -> "${it.town}, ${it.state}"
                                 }
                             },
                             currentUnits = state.data.currentUnits
                         ))
                     }
                )
            }

            State.NoConnection -> {
                Toast.makeText(context, "No internet connection", Toast.LENGTH_SHORT).show()
            }
        }
    }

    AnimatedVisibility(
        visible = screenState is State.Loading,
        enter = fadeIn(
            animationSpec = tween(durationMillis = 0),
            initialAlpha = 0f,
        ), exit = fadeOut(
            animationSpec = tween(durationMillis = 500)
        )
    ) {
        Loader()
    }
}

@Composable
private fun MainScreenContent(
    state: MainScreenState,
    onMenu: () -> Unit,
    onSearch: () -> Unit,
    changeAnimation: (WeatherAnimation) -> Unit,
    onSkyCams: (SkyCamsData) -> Unit,
    onAlertClick: (Int) -> Unit,
    onStarClick: () -> Unit,
    onMapExpandClick: () -> Unit,
    onHourlyForecastClick: (Long) -> Unit,
    onDailyHourlyForecastClick: (Long) -> Unit,
    onChartDetailsClick: () -> Unit
) {
    var showChangeAnimationDialog by remember { mutableStateOf(false) }

    AnimatedWeather(
        type = state.weatherAnimation,
        animationSize = AnimationSize.Large,
        modifier = Modifier.fillMaxSize()
    )

    val scrollState = rememberLazyListState()

    LaunchedEffect(true) {
        scrollState.scrollToItem(0)
    }

    val scrolledDown by remember { derivedStateOf { scrollState.canScrollBackward } }
    val toolbarBackgroundAlpha by animateFloatAsState(
        targetValue = if (scrolledDown) 0.9f else 0.2f, label = "Toolbar color",
        animationSpec = tween(700)
    )
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        if (showChangeAnimationDialog) {
            WeatherSelectorDialog(
                onDismissRequest = { showChangeAnimationDialog = false },
                callback = {
                    changeAnimation(it)
                    showChangeAnimationDialog = false
                }
            )
        }
        var hourlyForecastDate by remember { mutableStateOf("") }
        LazyColumn(
            state = scrollState,
            horizontalAlignment = CenterHorizontally
        ) {
            item {
                Spacer(modifier = Modifier.height(60.dp + WindowInsets.statusBars.asPaddingValues().calculateTopPadding()))
                TodayForecast(
                    data = state.todayForecastData,
                    alerts = state.alertList.toPersistentList(),
                    onAlertClick = { onAlertClick(it) },
                )
            }

            item {
                if (state.forecastDetails != null) {
                    ForecastDetails(
                        data = state.forecastDetails,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )
                } else {
                    NoDataHolder(modifier = Modifier.padding(horizontal = 16.dp))
                }
                Spacer(modifier = Modifier.height(16.dp))
            }

            item {
                if (state.adsEnabled) {
                    PinnableContainer()

                    AdMobBanner()
                    Spacer(modifier = Modifier.height(25.dp))
                }
            }

            item {
                ForecastWeatherLiveHeader(
                    onMapExpandClick = { onMapExpandClick() },
                    isButtonVisible = state.weatherLiveUrl.isNotEmpty(),
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))
            }

            item {
                if (state.weatherLiveUrl.isNotEmpty()) {
                    PinnableContainer()

                    ForecastWeatherLive(
                        liveMapUrl = state.weatherLiveUrl,
                        onMapExpandClick = { onMapExpandClick() },
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )
                } else {
                    NoDataHolder(modifier = Modifier.padding(horizontal = 16.dp))
                }
                Spacer(modifier = Modifier.height(25.dp))
            }

            item {
                ForecastHeader(
                    date = hourlyForecastDate,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))
            }

            item {
                if (state.hourlyList.isNotEmpty()) {
                    ForecastHourly(
                        hourlyList = state.hourlyList.toPersistentList(),
                        currentDayListener = { hourlyForecastDate = it },
                        highlightCurrent = true,
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .clip(Shapes.large)
                            .background(MaterialTheme.colorScheme.inverseSurface)
                            .padding(vertical = 16.dp),
                        onItemClick = {
                            val item = state.hourlyList[it]
                            onHourlyForecastClick(item.timeLong)
                        }
                    )
                } else {
                    NoDataHolder(modifier = Modifier.padding(horizontal = 16.dp))
                }
                Spacer(modifier = Modifier.height(16.dp))
            }

            item {
                if (state.dailyList.isNotEmpty()) {
                    ForecastDaily(
                        dailyList = state.dailyList,
                        modifier = Modifier.padding(horizontal = 16.dp),
                        onDailyHourClick = onDailyHourlyForecastClick
                    )
                } else {
                    NoDataHolder(modifier = Modifier.padding(horizontal = 16.dp))
                }
                Spacer(modifier = Modifier.height(25.dp))
            }

            item {
                SkyCamsHeader(modifier = Modifier.padding(horizontal = 16.dp))
                if (state.skyCamsData != null) {
                    SkyCams(
                        skyCamsData = state.skyCamsData,
                        modifier = Modifier.padding(16.dp),
                        onClick = { onSkyCams(state.skyCamsData) },
                    )
                } else {
                    NoDataHolder(modifier = Modifier.padding(16.dp))
                }
                Spacer(modifier = Modifier.height(25.dp))
            }

            item {
                val hourlyReports = state.hourlyReports
                val dailyForecastReports = state.dailyForecastReports
                val currentUnits = state.currentUnits
                val locationName = state.topBarData.let {
                    "${it.town}, ${it.state}"
                }

                Box(modifier = Modifier.padding(horizontal = 16.dp)) {
                    EnhancedWeatherChartWidget(
                        hourlyReports = hourlyReports,
                        dailyForecastReports = dailyForecastReports,
                        modifier = Modifier.fillMaxWidth(),
                        currentUnits = currentUnits
                    )
                    Box(
                        modifier = Modifier
                            .matchParentSize()
                            .clip(MaterialTheme.shapes.large)
                            .clickable(
                                indication = null,
                                interactionSource = remember { MutableInteractionSource() }
                            ) {
                                onChartDetailsClick()
                            }
                    )
                }
                Spacer(modifier = Modifier.height(110.dp))
            }
        }
        if (BuildConfig.TEST_OPTION_ENABLED) {
            AppButton(
                text = "Animation",
                onClick = { showChangeAnimationDialog = true },
                backgroundColor = Color.Transparent,
                modifier = Modifier
                    .align(TopEnd)
                    .padding(16.dp)
                    .padding(top = 100.dp)
            )
        }
    }
    Column {
        TopBar(
            data = state.topBarData,
            icons = mutableSetOf<TopBarIcon>().apply {
                when (state.starState) {
                    StarState.Active -> add(TopBarIcon.Star(true) { onStarClick() })
                    StarState.Inactive -> add(TopBarIcon.Star(false) { onStarClick() })
                    StarState.Hidden -> Unit
                }
                add(TopBarIcon.Menu { onMenu() })
                add(TopBarIcon.Search { onSearch() })
            }.toPersistentSet(),
            background = MaterialTheme.colorScheme.inverseSurface.copy(alpha = toolbarBackgroundAlpha)
        )
        Spacer(modifier = Modifier.height(12.dp))

    }
}

@Preview
@Composable
fun MainScreenPreview() {
    MainScreenContent(
        state = MainScreenState(
            topBarData = TopBarData.Place(
                town = "Springfield",
                state = "IL",
                updatedMinutesAgo = "5"
            ),
            isConnectionAvailable = true,
            weatherAnimation = WeatherAnimation.MostlyCloudyNight,
            todayForecastData = TodayForecastData(
                temp = "72°F",
                icon = R.drawable.stat_notify_error,
                description = "Partly Cloudy",
                feelsLike = "70°F",
                isFeelsLikeVisible = true
            ),
            forecastDetails = null,
            hourlyList = emptyList<ForecastHourlyItemData>().toPersistentList(),
            dailyList = emptyList<ForecastDailyItemData>().toPersistentList(),
            hourlyReports = emptyList(),
            dailyForecastReports = emptyList(),
            weatherLiveUrl = "",
            skyCamsStreamAvailable = false,
            skyCamsData = null,
            place = null,
            adsEnabled = false,
            starState = StarState.Hidden,
            isRefreshing = false,
            alertList = emptyList<AlertItemData>().toPersistentList(),
            currentUnits = Units.English
        ),
        onMapExpandClick = { },
        onMenu = { },
        onSearch = { },
        changeAnimation = { },
        onSkyCams = { },
        onAlertClick = { },
        onStarClick = { },
        onHourlyForecastClick = { },
        onDailyHourlyForecastClick = {},
        onChartDetailsClick = {}
    )
}