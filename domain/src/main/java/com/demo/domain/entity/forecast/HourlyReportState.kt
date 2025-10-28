package com.demo.domain.entity.forecast

import com.demo.domain.entity.common.Units
import java.time.ZoneId

data class HourlyReportState(
    val hourlyForecast: List<HourlyReport>,
    val units: Units,
    val zoneId: ZoneId?,
)
