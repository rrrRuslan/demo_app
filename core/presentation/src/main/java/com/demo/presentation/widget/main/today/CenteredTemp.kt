package com.demo.presentation.widget.main.today

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.demo.common.extensions.extractNumbers

@Composable
fun CenteredTemp(text: String) {
    val isNegative = text.contains("-")
    val numbers = text.extractNumbers()
    val remainingText = text.replace(numbers.joinToString(""), "")
    val symbol = if (isNegative) remainingText.replace("-", "") else remainingText
    val temp = numbers.joinToString("")
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        if (isNegative) {
            Spacer(modifier = Modifier.weight(0.75f))
            Text(
                text = "-",
                fontSize = 96.sp,
                color = MaterialTheme.colorScheme.onBackground,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.headlineSmall
            )
        } else {
            Spacer(modifier = Modifier.weight(1f))
        }
        Text(
            text = temp,
            fontSize = 96.sp,
            color = MaterialTheme.colorScheme.onBackground,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.headlineSmall
        )
        Text(
            text = symbol,
            fontSize = 96.sp,
            color = MaterialTheme.colorScheme.onBackground,
            textAlign = TextAlign.Start,
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.weight(1f)
        )
    }
}
