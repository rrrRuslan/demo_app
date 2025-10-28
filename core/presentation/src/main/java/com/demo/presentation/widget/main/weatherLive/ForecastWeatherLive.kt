package com.demo.presentation.widget.main.weatherLive

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.demo.presentation.R
import com.demo.presentation.widget.liveMap.map.LiveMap
import com.demo.theme.AppTheme

@SuppressLint("SetJavaScriptEnabled", "ClickableViewAccessibility")
@Composable
fun ForecastWeatherLive(
    liveMapUrl: String,
    onMapExpandClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        Box(
            modifier = Modifier.clip(MaterialTheme.shapes.large)
        ) {
            LiveMap(
                liveMapUrl = liveMapUrl,
                coordinates = null,
                isInteractive = false,
                modifier = Modifier
                    .height(230.dp)
                    .fillMaxWidth()
            )
            Box(
                modifier = Modifier
                    .height(230.dp)
                    .fillMaxWidth()
                    .background(Color.Transparent)
                    .clickable { onMapExpandClick() },
            )
            Box(
                modifier = Modifier
                    .padding(12.dp)
                    .size(40.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(MaterialTheme.colorScheme.inverseSurface)
                    .align(Alignment.TopEnd)
                    .clickable { onMapExpandClick() }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_fullscreen),
                    contentDescription = "Live map",
                    tint = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier
                        .size(18.dp)
                        .align(Alignment.Center)
                )
            }
        }
    }
}

@Preview
@Composable
fun ForecastWeatherLivePreview() {
    AppTheme {
        ForecastWeatherLive(liveMapUrl = "", { })
    }
}