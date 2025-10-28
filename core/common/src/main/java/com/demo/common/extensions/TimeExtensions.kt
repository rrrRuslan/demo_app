package com.demo.common.extensions

import java.time.Duration
import java.util.concurrent.TimeUnit

fun Long.formatTime(): String {
    val min = TimeUnit.MILLISECONDS.toMinutes(this)
    val hours = TimeUnit.MILLISECONDS.toHours(this)
    val days = TimeUnit.MILLISECONDS.toDays(this)
    return when {
        min < 60 -> "$min ${if (min == 1L) "minute" else "minutes"}"
        days > 0 -> hours.hoursToDays(hours.toInt())
        hours > 0 -> min.minutesToHours()
        else -> ""
    }
}

fun Long.minutesToHours(): String {
    val hours = this / 60
    val remainingMinutes = this % 60
    return "${hours}h ${remainingMinutes}m"
}

fun Long.hoursToDays(hours: Int): String {
    val days = hours / 24
    val remainingHours = hours % 24
    return "${days}d ${remainingHours}h"
}

fun Long.millisToReadableTimeFormat(): String {
    val duration = Duration.ofMillis(this)
    val minutes = duration.toMinutes()
    val seconds = duration.minusMinutes(minutes).seconds
    return "$minutes:${if (seconds > 9) "" else "0"}$seconds"
}