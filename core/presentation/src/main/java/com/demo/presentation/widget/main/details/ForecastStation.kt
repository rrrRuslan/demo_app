package com.demo.presentation.widget.main.details

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.demo.presentation.R
import com.demo.theme.AppTheme
import com.demo.theme.Shapes

@Composable
fun ForecastStation(
    station: String,
    modifier: Modifier = Modifier,
) {
//    var isPopupVisible by remember { mutableStateOf(false) }
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .padding(6.dp)
            .clip(Shapes.medium)
//            .clickable { isPopupVisible = !isPopupVisible }
    ) {
//        Icon(
//            painter = painterResource(id = R.drawable.ic_point),
//            tint = MaterialTheme.colorScheme.onSurfaceVariant,
//            contentDescription = "Search",
//            modifier = Modifier
//                .height(10.dp)
//        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = stringResource(R.string.station),
            fontSize = 11.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(modifier = Modifier.width(4.dp))
        Box {
//            if (isPopupVisible) {
//                Popup(
//                    alignment = Alignment.TopEnd,
//                    offset = IntOffset(0, -120),
//                    onDismissRequest = { isPopupVisible = false },
//                ) {
//                    Box(
//                        modifier = Modifier
//                            .clip(Shapes.medium)
//                            .background(Color.White)
//                            .clickable { isPopupVisible = false }
//                            .padding(12.dp)
//                    ) {
//                        Text(
//                            text = station,
//                            fontSize = 11.sp,
//                            color = Color.Black,
//                            style = MaterialTheme.typography.titleMedium
//                        )
//                    }
//                }
//            }
            Text(
                text = station,
                fontSize = 13.sp,
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}

@Preview
@Composable
fun ForecastStationPreview() {
    AppTheme {
        ForecastStation("EW6175 La (E6175)EW6175 La Marque TX US (E6175)EW6175 La Marque TX US (E6175)")
    }
}