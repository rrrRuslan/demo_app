package com.demo.presentation.widget.main.details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.demo.presentation.widget.main.details.item.ForecastDetailItemData
import com.demo.presentation.widget.main.details.item.ForecastDetailsItem
import com.demo.theme.AppTheme
import com.demo.theme.Shapes

@Composable
fun ForecastDetails(
    data: ForecastDetailsItemData,
    modifier: Modifier = Modifier,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .clip(Shapes.large)
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.inverseSurface)
            .padding(horizontal = 35.dp, vertical = 28.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            ForecastDetailsItem(ForecastDetailItemData.Dewpoint(data.dewpoint))
            ForecastDetailsItem(ForecastDetailItemData.Humidity(data.humidity))
            ForecastDetailsItem(ForecastDetailItemData.WindChill(data.windChill))
            ForecastDetailsItem(ForecastDetailItemData.HeatIndex(data.heatIndex))
        }
        Spacer(modifier = Modifier.height(18.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            ForecastDetailsItem(ForecastDetailItemData.Winds(data.winds))
            ForecastDetailsItem(ForecastDetailItemData.Pressure(data.pressure))
            ForecastDetailsItem(ForecastDetailItemData.Precip(data.precip))
        }
        Spacer(modifier = Modifier.height(18.dp))
        ForecastStation(station = data.station)
    }
}

@Preview
@Composable
fun ForecastDetailsPreview() {
    AppTheme {
        ForecastDetails(
            data = ForecastDetailsItemData.Companion.test()
        )
    }
}