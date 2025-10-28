package com.demo.domain.entity.common

enum class Units(val value: String) {
    English("e"),
    Metric("m");

    val mark: String
        get() = when (this) {
            English -> "°F"
            Metric -> "°C"
        }

    val symbol: String
        get() = when (this) {
            English -> "°"
            Metric -> "°"
        }
}