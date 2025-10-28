package com.demo.presentation.widget.main.forecast.daily

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun ForecastDaily(
    dailyList: List<ForecastDailyItemData>,
    modifier: Modifier = Modifier,
    onDailyHourClick: (Long) -> Unit
) {
    var dailyListState by remember { mutableStateOf(dailyList) }
    Column(
        modifier = modifier
            .clip(MaterialTheme.shapes.large)
            .background(MaterialTheme.colorScheme.inverseSurface)
            .padding(top = 16.dp, bottom = 16.dp)
    ) {
        dailyListState.forEachIndexed { index, item ->
            DayForecast(
                data = item,
                onDayExpanded = {
                    dailyListState = dailyListState.mapIndexed { i, dailyForecast ->
                        if (index == i) {
                            dailyForecast.copy(isExpanded = !dailyForecast.isExpanded)
                        } else {
                            dailyForecast.copy(isExpanded = false)
                        }
                    }
                },
                onHourClick = { onDailyHourClick(it) }
            )
            Spacer(modifier = Modifier.width(8.dp))
        }
    }
}

@Preview
@Composable
fun DailyForecastPreview() {
    ForecastDaily(
        dailyList = ForecastDailyItemData.Companion.testList(),
        onDailyHourClick = { }
    )
}