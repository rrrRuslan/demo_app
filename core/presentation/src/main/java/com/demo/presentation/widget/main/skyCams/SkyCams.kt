package com.demo.presentation.widget.main.skyCams

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.demo.presentation.widget.main.skyCams.data.SkyCamsData
import com.demo.presentation.widget.main.mediaContent.StaticImageContent
import com.demo.presentation.widget.main.mediaContent.data.MediaType
import com.demo.theme.AppTheme
import com.demo.theme.BalticSeaVariant3
import com.demo.theme.Shapes

@Composable
fun SkyCams(
    skyCamsData: SkyCamsData,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .fillMaxWidth()
            .clip(Shapes.large)
            .background(MaterialTheme.colorScheme.inverseSurface)
            .clickable { onClick() }
    ) {
        StaticImageContent(skyCamsData.imageUrl)

        Text(
            text = skyCamsData.location,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(8.dp)
                .background(
                    color = BalticSeaVariant3.copy(alpha = 0.9f),
                    shape = Shapes.medium
                )
                .padding(
                    horizontal = 8.dp,
                    vertical = 2.dp
                ),
            color = Color.White,
            fontSize = 12.sp
        )
    }
}

@Composable
@Preview
fun SkyCamsPreview() {
    AppTheme {
        SkyCams(
            skyCamsData = SkyCamsData(
                imageUrl = "",
                mediaType = MediaType.StaticImage(""),
                location = "New York, NY",
                lastUpdateTime = null,
                lastUpdateTimeFormatted = "Last updated: 10:00 AM",
                state = "NY"
            ),
            onClick = {},
        )
    }
}