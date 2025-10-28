package com.demo.theme.appTheme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import com.demo.theme.BalticSea
import com.demo.theme.BalticSeaTransparent
import com.demo.theme.BalticSeaVariant
import com.demo.theme.BalticSeaVariant1
import com.demo.theme.BalticSeaVariant2
import com.demo.theme.BalticSeaVariantUltraLight
import com.demo.theme.BeautyBush
import com.demo.theme.CongressBlue
import com.demo.theme.Cornflower
import com.demo.theme.Mariner
import com.demo.theme.MarinerTransparent
import com.demo.theme.MountainMistLight
import com.demo.theme.MulledWine
import com.demo.theme.Silver
import com.demo.theme.Sunglo
import com.demo.theme.WoodsmokeLight

val LocalCustomColorsPalette = staticCompositionLocalOf { AppColors() }

val MaterialTheme.appColors: AppColors
    @Composable
    @ReadOnlyComposable
    get() = LocalCustomColorsPalette.current

sealed class WeatherAppTheme {
    object Day : WeatherAppTheme()
    object Night : WeatherAppTheme()

    val palette: AppColors
        get() = when (this) {
            Day -> AppColors(
                lightColorScheme(
                    background = BalticSea,
                    onBackground = Color.White,
                    scrim = BalticSeaTransparent,

                    onSurface = Color.White,
                    onSurfaceVariant = Silver,
                    surfaceVariant = WoodsmokeLight,

                    surface = BalticSeaVariant,
                    inverseSurface = BalticSeaVariantUltraLight,

                    secondary = Mariner,

                    tertiary = MountainMistLight,

                    outline = Color.Transparent,
                    outlineVariant = MarinerTransparent,

                    error = Sunglo,
                    errorContainer = MulledWine,
                    onErrorContainer = Color.White,

                    primaryContainer = BalticSeaVariant,
                ),
                hourlyActive = Mariner,
                tempHigh = BeautyBush,
                tempLow = Cornflower
            )

            Night -> AppColors(
                darkColorScheme(
                    background = BalticSea,
                    onBackground = Color.White,
                    scrim = BalticSeaTransparent,

                    onSurface = Color.White,
                    onSurfaceVariant = Silver,
                    surfaceVariant = WoodsmokeLight,

                    surface = BalticSeaVariant1,
                    inverseSurface = BalticSeaVariant2,

                    secondary = BalticSeaVariant,

                    tertiary = MountainMistLight,

                    outline = CongressBlue,
                    outlineVariant = MarinerTransparent,

                    error = Sunglo,
                    errorContainer = MulledWine,
                    onErrorContainer = Color.White,

                    primaryContainer = BalticSeaVariant,
                ),
                hourlyActive = Mariner,
                tempHigh = BeautyBush,
                tempLow = Cornflower
            )
        }
}
