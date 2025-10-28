package com.demo.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import com.demo.theme.appTheme.LocalCustomColorsPalette
import com.demo.theme.appTheme.WeatherAppTheme

@Composable
fun AppTheme(theme: WeatherAppTheme = WeatherAppTheme.Night, content: @Composable () -> Unit) {
    CompositionLocalProvider(LocalCustomColorsPalette provides theme.palette) {
        MaterialTheme(
            colorScheme = LocalCustomColorsPalette.current.material,
            typography = Typography,
            shapes = Shapes,
            content = content
        )
    }
}