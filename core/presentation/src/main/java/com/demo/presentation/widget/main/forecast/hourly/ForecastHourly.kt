package com.demo.presentation.widget.main.forecast.hourly

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toPersistentList

@Composable
fun ForecastHourly(
    hourlyList: ImmutableList<ForecastHourlyItemData>,
    highlightCurrent: Boolean,
    modifier: Modifier = Modifier,
    state: LazyListState = rememberLazyListState(),
    currentHourIndex: Int = 0,
    currentDayListener: ((String) -> Unit)? = null,
    onItemClick: ((Int) -> Unit)? = null,
    contentPadding: PaddingValues = PaddingValues(horizontal = 16.dp),
    hourForecastWidth: Dp = 46.dp
) {
    LazyRow(
        state = state,
        verticalAlignment = Alignment.CenterVertically,
        contentPadding = contentPadding,
        modifier = modifier
    ) {
        items(hourlyList.size) {
            val item = if (highlightCurrent) {
                hourlyList[it].copy(isActive = it == currentHourIndex)
            } else {
                hourlyList[it]
            }
            HourForecast(
                data = item,
                onClick = { onItemClick?.invoke(it) },
                hourForecastWidth = hourForecastWidth
            )
            Spacer(modifier = Modifier.width(8.dp))
        }
    }
    if (currentDayListener != null) {
        LaunchedEffect(state) {
            snapshotFlow { state.firstVisibleItemIndex }
                .collect {
                    currentDayListener(hourlyList[it].date)
                }
        }
    }
}

@Preview
@Composable
fun ForecastHourlyPreview() {
    ForecastHourly(
        hourlyList = ForecastHourlyItemData.Companion.testList().toPersistentList(),
        modifier = Modifier.height(120.dp),
        highlightCurrent = false,
        onItemClick = null,
        currentDayListener = {}
    )
}