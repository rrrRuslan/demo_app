package com.demo.demo_app.navigation

import com.demo.presentation.widget.main.alerts.AlertItemData

sealed class NavigationEvent {
    data object BackPressed : NavigationEvent()
    data object Main : NavigationEvent()
    data object Alerts : NavigationEvent()
    data object Favorites : NavigationEvent()
    data object Gps : NavigationEvent()
    data object LiveMap : NavigationEvent()
    data object Menu : NavigationEvent()
    data object Settings : NavigationEvent()
    data object Units : NavigationEvent()
    data object AboutApp : NavigationEvent()
    data object OpenDrawer : NavigationEvent()
    data object Test : NavigationEvent()
    data object CreateAccount : NavigationEvent()
    data object NeedHelp : NavigationEvent()
    data object AccountRecovery : NavigationEvent()
    data object MyAccount : NavigationEvent()
    data object ContactSupport : NavigationEvent()
    data object AccountCreated : NavigationEvent()
    data object SignIn : NavigationEvent()
    data class ConfirmAccount(val autoResend: Boolean) : NavigationEvent()
    data class AlertInfo(val alertInfo: AlertItemData) : NavigationEvent()
    data object LiveSkyCams : NavigationEvent()

    data class FullscreenImage(
        val url: String,
        val name: String,
        val state: String,
        val date: Long
    ) : NavigationEvent()

    data class HourlyReport(val timeLong: Long, val isSpecificDay: Boolean = false) :
        NavigationEvent()
    
    data class NavigateToChartDetail(
        val locationName: String,
        val currentUnits: Units
    ) : NavigationEvent()
}