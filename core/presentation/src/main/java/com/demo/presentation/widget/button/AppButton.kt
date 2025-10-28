package com.demo.presentation.widget.button

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.demo.theme.AppTheme
import com.demo.theme.Emperor
import com.demo.theme.MarinerVariant

@Composable
fun AppButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    textColor: Color = Color.White,
    isEnabled: Boolean = true,
    border: BorderStroke? = null,
    backgroundColor: Color = MarinerVariant,
    type: AppButtonType = AppButtonType.Small,
) {
    val customModifier = when (type) {
        AppButtonType.Small -> Modifier.widthIn(60.dp)
        AppButtonType.Default -> Modifier.fillMaxWidth()
    }
    val clickModifier = if (isEnabled) Modifier
        .clip(MaterialTheme.shapes.extraLarge)
        .clickable { onClick() } else Modifier
    val color = if (isEnabled) backgroundColor else Emperor
    Surface(
        modifier = modifier
            .height(type.height)
            .then(customModifier)
            .then(clickModifier),
        color = color,
        border = border,
        shape = MaterialTheme.shapes.extraLarge
    ) {
        Box(
            modifier = customModifier,
            contentAlignment = Alignment.Center
        ) {
            Text(
                modifier = Modifier.padding(horizontal = 10.dp),
                text = text,
                color = textColor,
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center,
            )
        }
    }
}

@Preview
@Composable
fun AppButtonPreview() {
    AppTheme {
        AppButton(
            text = "Expand",
            type = AppButtonType.Default,
            onClick = { }
        )
    }
}