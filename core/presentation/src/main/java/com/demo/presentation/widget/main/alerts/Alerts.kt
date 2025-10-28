package com.demo.presentation.widget.main.alerts

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun Alerts(
    alerts: List<AlertItemData>,
    modifier: Modifier = Modifier,
    onAlertClick: (AlertItemData) -> Unit
) {
    LazyRow(
        modifier = modifier,
        contentPadding = PaddingValues(horizontal = 24.dp)
    ) {
        items(alerts.size) { index ->
            val item = alerts[index]
            AlertItem(
                alertItemData = item,
                onClick = { onAlertClick(item) }
            )
        }
    }
}

@Preview
@Composable
fun AlertsPreview() {
    Alerts(
        alerts = AlertItemData.getTestList(),
        onAlertClick = { }
    )
}