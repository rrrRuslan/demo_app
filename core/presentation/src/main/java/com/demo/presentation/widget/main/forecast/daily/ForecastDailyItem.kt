package com.demo.presentation.widget.main.forecast.daily

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.demo.presentation.R
import com.demo.presentation.widget.main.forecast.hourly.ForecastHourly
import com.demo.theme.AppTheme
import com.demo.theme.appTheme.appColors
import kotlinx.collections.immutable.toPersistentList

@Composable
fun DayForecast(
    data: ForecastDailyItemData,
    onDayExpanded: () -> Unit,
    onHourClick: (Long) -> Unit,
    modifier: Modifier = Modifier,
) {
    val arrowRotation by animateFloatAsState(targetValue = if (!data.isExpanded) 0f else 180f)
    Column(
        modifier = modifier.clickable { onDayExpanded() }
    ) {
        Row(
            modifier = modifier
                .padding(
                    horizontal = 16.dp,
                    vertical = 12.dp
                ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = data.day,
                fontSize = 13.sp,
                maxLines = 1,
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.weight(1f)
            )
            Icon(
                painter = painterResource(id = data.icon),
                tint = MaterialTheme.colorScheme.onSurface,
                contentDescription = "",
                modifier = Modifier
                    .weight(1f)
                    .size(15.dp)
            )
            Text(
                text = data.precipitation,
                fontSize = 13.sp,
                color = MaterialTheme.colorScheme.onSurface,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.weight(1f)
            )
            Text(
                text = data.tempMin,
                fontSize = 13.sp,
                textAlign = TextAlign.Center,
                color = MaterialTheme.appColors.tempLow,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.weight(1f)
            )
            Text(
                text = data.tempMax,
                fontSize = 13.sp,
                textAlign = TextAlign.Center,
                color = MaterialTheme.appColors.tempHigh,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(30.dp))
            Icon(
                painter = painterResource(id = R.drawable.ic_arrow),
                tint = MaterialTheme.colorScheme.onSurface,
                contentDescription = "",
                modifier = Modifier
                    .rotate(arrowRotation)
            )
        }
        AnimatedVisibility(visible = data.isExpanded) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp)
                    .height(120.dp)
            ) {
                ForecastHourly(
                    hourlyList = data.hourlyList.toPersistentList(),
                    highlightCurrent = false,
                    modifier = Modifier.height(120.dp),
                    onItemClick = {
                        val time = data.hourlyList[it]
                        onHourClick(time.timeLong)
                    }
                )
            }
        }
    }
}

@Preview
@Composable
fun DayForecastPreview() {
    AppTheme {
        DayForecast(
            data = ForecastDailyItemData.Companion.testList()[0],
            onDayExpanded = { },
            onHourClick = { }
        )
    }
}