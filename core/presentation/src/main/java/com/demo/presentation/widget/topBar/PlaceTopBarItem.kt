package com.demo.presentation.widget.topBar

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.demo.presentation.R
import com.demo.presentation.widget.topBar.data.TopBarData
import com.demo.theme.Alto
import com.demo.theme.sfProFamily

@Composable
fun PlaceTopBarItem(
    topBarPlaceData: TopBarData.Place,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.Start
    ) {
        val city = buildAnnotatedString {
            withStyle(
                SpanStyle(
                    fontFamily = sfProFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp
                )
            ) {
                append("${topBarPlaceData.town}, ")
            }
            withStyle(
                SpanStyle(
                    fontFamily = sfProFamily,
                    fontWeight = FontWeight.Normal,
                    fontSize = 15.sp
                )
            ) {
                append(topBarPlaceData.state)
            }
        }
        Text(
            text = city,
            color = Color.White,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Spacer(modifier = Modifier.height(2.dp))
        Text(
            text = stringResource(id = R.string.last_time_weather_update, topBarPlaceData.updatedMinutesAgo),
            style = MaterialTheme.typography.titleMedium,
            maxLines = 1,
            fontSize = 14.sp,
            overflow = TextOverflow.Ellipsis,
            color = Alto,
        )
    }
}

@Preview
@Composable
fun PlaceTopBarItemPreview() {
    PlaceTopBarItem(
        topBarPlaceData = TopBarData.Place(
            town = "San Francisco",
            state = "CA",
            updatedMinutesAgo = "5"
        )
    )
}