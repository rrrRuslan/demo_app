package com.demo.presentation.widget.main.weatherLive

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.demo.presentation.R
import com.demo.presentation.widget.button.outline.OutlineAppButton
import com.demo.theme.AppTheme

@Composable
fun ForecastWeatherLiveHeader(
    onMapExpandClick: () -> Unit,
    isButtonVisible: Boolean,
    modifier: Modifier = Modifier,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.fillMaxWidth()
    ) {
        Text(
            text = stringResource(R.string.weatherlive),
            fontSize = 22.sp,
            color = Color.White,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.weight(1f)
        )
        if (isButtonVisible) {
            OutlineAppButton(
                text = stringResource(R.string.expand),
                onClick = { onMapExpandClick() }
            )
        }
    }
}

@Preview
@Composable
fun ForecastWeatherLiveHeaderPreview() {
    AppTheme {
        ForecastWeatherLiveHeader(isButtonVisible = true, onMapExpandClick = { })
    }
}

