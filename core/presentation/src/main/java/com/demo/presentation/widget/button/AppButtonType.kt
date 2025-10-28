package com.demo.presentation.widget.button

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

enum class AppButtonType {
    Small,
    Default;

    val height: Dp
        get() = when (this) {
            Small -> 28.dp
            Default -> 44.dp
        }
}