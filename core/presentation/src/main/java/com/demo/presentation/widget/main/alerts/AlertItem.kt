package com.demo.presentation.widget.main.alerts

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.demo.presentation.R
import com.demo.theme.Shapes
import com.demo.theme.WoodsmokeUltraLight

@Composable
fun AlertItem(
    alertItemData: AlertItemData,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .padding(horizontal = 6.dp)
            .clip(Shapes.medium)
            .clickable { onClick() }
            .background(MaterialTheme.colorScheme.errorContainer)
            .padding(horizontal = 8.dp)
            .heightIn(28.dp)
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(24.dp)
                .clip(CircleShape)
                .background(WoodsmokeUltraLight)
        ) {
            Image(
                painter = painterResource(id = alertItemData.icon),
                contentDescription = alertItemData.text,
                modifier = Modifier
                    .size(16.dp)
                    .clickable { onClick() }
            )
        }
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = alertItemData.text,
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onErrorContainer,
            fontSize = 13.sp,
            textAlign = TextAlign.Center
        )
    }
}

@Preview
@Composable
fun AlertItemPreview() {
    AlertItem(AlertItemData(0, "Test", R.drawable.ic_alert_blue), { })
}