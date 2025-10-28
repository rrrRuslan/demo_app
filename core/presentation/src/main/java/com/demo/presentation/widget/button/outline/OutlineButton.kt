package com.demo.presentation.widget.button.outline

import androidx.compose.foundation.BorderStroke
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.demo.theme.AppTheme

@Composable
fun OutlineAppButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    OutlinedButton(
        onClick = {
            onClick()
        },
        border = BorderStroke(
            width = 1.dp,
            color = MaterialTheme.colorScheme.onBackground,
        ),
        modifier = modifier
    ) {
        Text(
            text = text,
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.onBackground,
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center,
        )
    }

}

@Preview
@Composable
fun OutlineAppButtonPreview() {
    AppTheme {
        OutlineAppButton(
            text = "Expand",
            onClick = { }
        )
    }
}