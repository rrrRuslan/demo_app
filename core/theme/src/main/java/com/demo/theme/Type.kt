package com.demo.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight

val sfProFamily = FontFamily(
    Font(R.font.sf_pro_display_regular, FontWeight.Normal),
    Font(R.font.sf_pro_display_medium, FontWeight.Medium),
    Font(R.font.sf_pro_display_bold, FontWeight.Bold),
    Font(R.font.sf_pro_display_semibold, FontWeight.SemiBold),
    Font(R.font.sf_pro_display_thin, FontWeight.Thin),
    Font(R.font.sf_pro_display_ultralight, FontWeight.ExtraLight)
)

val Typography = Typography(
    bodyMedium = TextStyle(
        fontFamily = sfProFamily,
        fontWeight = FontWeight.SemiBold
    ),
    displayMedium = TextStyle(
        fontFamily = sfProFamily,
        fontWeight = FontWeight.Medium
    ),
    titleMedium = TextStyle(
        fontFamily = sfProFamily,
        fontWeight = FontWeight.Normal
    ),
    titleLarge = TextStyle(
        fontFamily = sfProFamily,
        fontWeight = FontWeight.Bold
    ),
    headlineSmall = TextStyle(
        fontFamily = sfProFamily,
        fontWeight = FontWeight.Thin
    )
)
