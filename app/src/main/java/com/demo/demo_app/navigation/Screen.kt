package com.demo.demo_app.navigation

sealed class Screen(val route: String) {

    data object Splash : Screen("splash_screen")
    data object Onboarding : Screen("onboarding_screen")
    data object Root : Screen("root_screen")
    data object InitialSearch : Screen("initial_search_screen")
    data object Search : Screen("search_screen")
    data object Units : Screen("units_screen")
    data object AboutApp : Screen("about_app_screen")
    data object TropicalCenter : Screen("tropical_center_screen")
    data object FullscreenImage : Screen("fullscreen_image_screen")
    data object LiveSkyCams : Screen("live_sky_cams_screen")
    data object SettingsAboutApp : Screen("settings_about_app_screen")
    data object Settings : Screen("settings_screen")
    data object AlertInfo : Screen("alert_info_screen")
    data object Test : Screen("test_screen")
    data object CreateAccount: Screen("create_account")
    data object ConfirmAccount: Screen("confirm_account")
    data object AccountCreated: Screen("account_created")
    data object AccountRecovery: Screen("account_recovery")
    data object SignIn: Screen("sign_in")
    data object MyAccount: Screen("my_account")
    data object UserAlerts: Screen("user_alerts")
    data object HourlyReport: Screen("hourly_report")
    data object ChartDetail: Screen("chart_detail_screen")

    fun withArgs(vararg args: String?): String {
        return buildString {
            append(route)
            args.forEach { arg ->
                append("/$arg")
            }
        }
    }

}