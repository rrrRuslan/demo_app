package com.demo.presentation.widget.main.details.item

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.demo.theme.AppTheme
import com.demo.theme.appTheme.WeatherAppTheme

@Composable
fun ForecastDetailsItem(
    forecastDetailItemData: ForecastDetailItemData,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .widthIn(65.dp)
            .heightIn(66.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            painter = painterResource(id = forecastDetailItemData.icon),
            tint = MaterialTheme.colorScheme.onSurface,
            contentDescription = forecastDetailItemData.value,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = stringResource(id = forecastDetailItemData.title),
            fontSize = 11.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            maxLines = 1,
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(modifier = Modifier.height(2.dp))
        Text(
            text = forecastDetailItemData.value,
            fontSize = 13.sp,
            maxLines = 1,
            color = MaterialTheme.colorScheme.onSurface,
            style = MaterialTheme.typography.titleMedium
        )
    }
}

@Preview
@Composable
fun ForecastDetailsItemPreview() {
    AppTheme(WeatherAppTheme.Day) {
        ForecastDetailsItem(ForecastDetailItemData.Humidity("78%"))
    }
}