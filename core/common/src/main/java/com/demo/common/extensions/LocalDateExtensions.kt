package com.demo.common.extensions

import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.TemporalAccessor
import java.util.Locale

fun TemporalAccessor.byPattern(pattern: String): String =
    DateTimeFormatter.ofPattern(pattern, Locale.US).format(this)

fun ZonedDateTime.isToday(zoneId: ZoneId?): Boolean {
    return try {
        if (zoneId == null)
            return false
        else
            this.toLocalDate().isEqual(LocalDateTime.now().atZone(zoneId).toLocalDate())
    } catch (e: Exception) {
        false
    }
}


fun ZonedDateTime.isTomorrow(zoneId: ZoneId?): Boolean {
    return try {
        if (zoneId == null)
            return false
        else
            this.toLocalDate().isEqual(LocalDateTime.now().atZone(zoneId).toLocalDate().plusDays(1))
    } catch (e: Exception) {
        false
    }
}

fun Long.toLocalDateTime(): LocalDateTime? {
    return try {
        LocalDateTime.ofInstant(
            Instant.ofEpochSecond(this),
            ZoneId.systemDefault()
        )
    } catch (e: Exception) {
        null
    }
}

fun Long.toZonedDateTime(timeZoneId: ZoneId): ZonedDateTime? {
    return try {
        ZonedDateTime.ofInstant(
            Instant.ofEpochSecond(this),
            timeZoneId
        )
    } catch (e: Exception) {
        null
    }
}