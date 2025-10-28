package com.demo.demo_app.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.demo.demo_app.ui.mapper.MainMapper
import com.demo.presentation.common.WeatherAnimation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.combineTransform
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val mainMapper: MainMapper,
    private val getWeatherStateUseCase: GetWeatherStateUseCase,
    private val changeFavoriteStateUseCase: ChangeFavoriteStateUseCase,
    private val weatherMonitoring: WeatherMonitoringManager,
    private val connectivityObserver: ConnectivityObserver,
    userPreferences: UserPreferences,
    favoritesRepository: FavoritesRepository,
    appPreferences: AppPreferences,
    settingsPreferences: SettingsPreferences,
) : ViewModel() {

    private val shouldRefresh = MutableStateFlow(false)
    private val scope = viewModelScope + errorHandler() + Dispatchers.IO

    init {
        viewModelScope.launch {
            if (settingsPreferences.weatherMonitoringState.first()) {
                weatherMonitoring.start()
            }
        }
    }

    private val testAnimation = MutableStateFlow<WeatherAnimation?>(null)

    @OptIn(ExperimentalCoroutinesApi::class)
    private val dataFlow = shouldRefresh.flatMapLatest { isRefreshing ->
        combine(
            appPreferences.coordinates.distinctUntilChanged(),
            settingsPreferences.units.distinctUntilChanged(),
            connectivityObserver.status(viewModelScope)
        ) { _, _, status ->
            if (isRefreshing) {
                Timber.d("MainScreenState data refreshing (forced)")
                if (status == ConnectivityStatus.Available) {
                    getWeatherStateUseCase(GetWeatherStateUseCaseState.FORCED)
                } else {
                    getWeatherStateUseCase(GetWeatherStateUseCaseState.CACHED)
                }
            } else {
                Timber.d("MainScreenState data refreshing")
                if (status == ConnectivityStatus.Available) {
                    getWeatherStateUseCase(GetWeatherStateUseCaseState.OPTIONAL)
                } else {
                    getWeatherStateUseCase(GetWeatherStateUseCaseState.CACHED)
                }
            }
        }.onEach {
            testAnimation.tryEmit(null)
            shouldRefresh.tryEmit(false)
        }
    }

    val mainScreenState = combineTransform(
        dataFlow,
        shouldRefresh,
        favoritesRepository.getFavoritesPlaces().distinctUntilChanged(),
        connectivityObserver.status(viewModelScope),
        testAnimation,
        userPreferences.userFeatures
    ) { data, isRefreshing, favorites, status, testAnimation, userFeatures ->
        if (!shouldRefresh.value) {
            emit(State.loading())
        }
        when (data) {
            null -> emit(State.error(ErrorModel.unknown()))
            else -> {
                val starState = when {
                    favorites.map { it.id }.contains(data.currentPlace?.id) -> StarState.Active
                    favorites.size == userFeatures.maxFavorites -> StarState.Hidden
                    else -> StarState.Inactive
                }
                val mappedData = mainMapper.map(data)
                val screenState = mappedData.copy(
                    starState = starState,
                    isConnectionAvailable = status == ConnectivityStatus.Available,
                    isRefreshing = isRefreshing,
                    weatherAnimation = testAnimation ?: mappedData.weatherAnimation,
                    adsEnabled = userFeatures.adsEnabled
                )
                emit(State.successes(screenState))
            }
        }
    }.catch {
        Timber.e(it)
        emit(State.error(ErrorModel.unknown()))
    }.stateIn(scope, SharingStarted.Eagerly, State.loading())

    fun changeAnimation(weatherAnimation: WeatherAnimation) {
        viewModelScope.launch { testAnimation.emit(weatherAnimation) }
    }

    fun refresh() {
        shouldRefresh.tryEmit(true)
    }

    fun changeFavoriteState() {
        viewModelScope.launch(Dispatchers.IO + errorHandler()) {
            changeFavoriteStateUseCase(mainScreenState.first().getStateData()?.place)
        }
    }

}

