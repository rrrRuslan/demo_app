package com.demo.presentation.widget.main.noDataHolder

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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

@Composable
fun NoDataHolder(
    modifier: Modifier = Modifier,
    text: String? = null,
) {

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .fillMaxWidth()
            .height(115.dp)
            .clip(MaterialTheme.shapes.large)
            .background(MaterialTheme.colorScheme.inverseSurface)
    ) {
        Text(
            text = text ?: stringResource(R.string.not_available),
            fontSize = 15.sp,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }

}

@Preview
@Composable
fun NoDataHolderPreview() {
    AppTheme {
        NoDataHolder()
    }
}