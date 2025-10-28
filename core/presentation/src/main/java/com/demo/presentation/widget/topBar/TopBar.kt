package com.demo.presentation.widget.topBar

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.demo.presentation.widget.topBar.data.TopBarData
import com.demo.presentation.widget.topBar.data.TopBarIcon
import com.demo.theme.Alto
import com.demo.theme.AppTheme
import com.demo.theme.Shapes

@Composable
fun TopBar(
    data: TopBarData,
    modifier: Modifier = Modifier,
    icons: Set<TopBarIcon> = emptySet(),
    background: Color = MaterialTheme.colorScheme.background,
) {
    val context = LocalContext.current
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .background(background)
            .windowInsetsPadding(WindowInsets.statusBars)
            .height(60.dp)
            .fillMaxWidth()
            .padding(horizontal = 12.dp)
    ) {
        icons.filter { it is TopBarIcon.Back || it is TopBarIcon.Menu }.forEach { icon ->
            Icon(
                painter = painterResource(id = icon.icon),
                contentDescription = stringResource(id = icon.description),
                tint = Color.White,
                modifier = Modifier
                    .size(32.dp)
                    .clip(Shapes.medium)
                    .clickable { icon.action() }
                    .padding(4.dp)
            )
        }
        Box(
            modifier = Modifier
                .horizontalScroll(rememberScrollState())
                .weight(1f)
                .padding(horizontal = 16.dp)
        ) {
            when (data) {
                is TopBarData.Place -> {
                    PlaceTopBarItem(topBarPlaceData = data)
                }

                else -> {
                    val nameTextSize = if (data.description != null) 15.sp else 18.sp
                    Column {
                        Text(
                            text = data.title(context),
                            color = Color.White,
                            maxLines = 1,
                            style = MaterialTheme.typography.titleLarge,
                            overflow = TextOverflow.Ellipsis,
                            fontSize = nameTextSize,
                            textAlign = TextAlign.Center
                        )
                        data.description?.let { text ->
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = text,
                                maxLines = 1,
                                style = MaterialTheme.typography.titleMedium,
                                overflow = TextOverflow.Ellipsis,
                                color = Alto,
                                fontSize = 14.sp,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            }
        }
        icons.filter { it is TopBarIcon.Search || it is TopBarIcon.Share || it is TopBarIcon.Add || it is TopBarIcon.Star }.forEach { icon ->
            Icon(
                painter = painterResource(id = icon.icon),
                contentDescription = stringResource(id = icon.description),
                tint = Color.White,
                modifier = Modifier
                    .padding(start = 12.dp)
                    .size(32.dp)
                    .clip(Shapes.medium)
                    .clickable { icon.action() }
                    .padding(4.dp)
            )
        }
    }
}

@Preview
@Composable
fun LiveMapTopBarPreview() {
    AppTheme {
        TopBar(
            data = TopBarData.LiveMapAndRadar,
            icons = setOf(TopBarIcon.Back { }, TopBarIcon.Search { }, TopBarIcon.Share { })
        )
    }
}