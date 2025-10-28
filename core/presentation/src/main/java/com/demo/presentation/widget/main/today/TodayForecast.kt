package com.demo.presentation.widget.main.today

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import com.demo.presentation.R
import com.demo.presentation.widget.main.alerts.AlertItemData
import com.demo.presentation.widget.main.alerts.Alerts
import com.demo.theme.AppTheme
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toPersistentList

@Composable
fun TodayForecast(
    data: TodayForecastData,
    alerts: ImmutableList<AlertItemData>,
    onAlertClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .height(400.dp)
            .fillMaxWidth(),
    ) {
        Alerts(
            alerts = alerts,
            onAlertClick = { onAlertClick(it.id) },
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 16.dp)
        )
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CenteredTemp(text = data.temp)
            if (data.isFeelsLikeVisible) {
                Text(
                    text = stringResource(id = R.string.feels_like, data.feelsLike),
                    fontSize = 13.sp,
                    color = MaterialTheme.colorScheme.onBackground,
                    style = MaterialTheme.typography.titleMedium
                )
            }
            Spacer(modifier = Modifier.height(36.dp))
            Image(
                painter = painterResource(id = data.icon),
                contentDescription = data.description,
                modifier = Modifier.size(70.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = data.description,
                fontSize = 20.sp,
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.titleLarge
            )
        }
    }
}

@Preview
@Composable
fun DayForecastPreview() {
    AppTheme {
        TodayForecast(
            data = TodayForecastData.Companion.test(),
            alerts = AlertItemData.getTestList().toPersistentList(),
            onAlertClick = { }
        )
    }
}