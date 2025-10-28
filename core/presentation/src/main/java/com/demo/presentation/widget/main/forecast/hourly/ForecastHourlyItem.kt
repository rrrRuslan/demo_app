package com.demo.presentation.widget.main.forecast.hourly

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.demo.presentation.R
import com.demo.theme.AppTheme
import com.demo.theme.appTheme.appColors

@Composable
fun HourForecast(
    data: ForecastHourlyItemData,
    onClick: (() -> Unit)?,
    modifier: Modifier = Modifier,
    hourForecastWidth: Dp = 46.dp
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        MaterialTheme
        val bg = if (!data.isActive) MaterialTheme.colorScheme.surfaceVariant else MaterialTheme.appColors.hourlyActive
        Text(
            text = data.time.lowercase(),
            fontSize = 11.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(modifier = Modifier.height(8.dp))
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .clip(MaterialTheme.shapes.medium)
                .background(bg)
                .width(hourForecastWidth)
                .height(95.dp)
                .border(
                    BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
                    MaterialTheme.shapes.medium
                )
                .then(
                    if (onClick == null) Modifier
                    else Modifier.clickable(onClick = onClick)
                )
        ) {
            Text(
                text = data.temp,
                fontSize = 13.sp,
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(6.dp))
            Icon(
                painter = painterResource(id = data.icon),
                tint = MaterialTheme.colorScheme.onSurface,
                contentDescription = "",
                modifier = Modifier.size(15.dp)
            )
            Spacer(modifier = Modifier.height(7.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_wind),
                    tint = MaterialTheme.colorScheme.onSurface,
                    contentDescription = "Wind direction",
                    modifier = Modifier
                        .size(8.dp)
                        .rotate(data.windAngle)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = data.windSpeed,
                    fontSize = 11.sp,
                    color = MaterialTheme.colorScheme.onSurface,
                    style = MaterialTheme.typography.titleLarge
                )
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = data.precipitation,
                fontSize = 11.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}

@Preview
@Composable
fun HourForecastPreview() {
    AppTheme {
        HourForecast(
            data = ForecastHourlyItemData.Companion.testList()[0],
            onClick = {}
        )
    }
}