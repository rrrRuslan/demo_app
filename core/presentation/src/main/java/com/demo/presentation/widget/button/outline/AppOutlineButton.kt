package com.demo.presentation.widget.button.outline

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.demo.theme.AppTheme
import com.demo.theme.Melrose


@Composable
fun AppOutlineButton(
    data: AppOutlineButtonData,
    onClick: () -> Unit,
    isSelected: Boolean,
    modifier: Modifier = Modifier,
) {
    val backgroundColor = if (isSelected)
        Melrose.copy(alpha = 0.15f)
    else MaterialTheme.colorScheme.background
    AppTheme {
        Surface(
            color = backgroundColor,
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.tertiary),
            shape = CircleShape,
            modifier = modifier
                .clip(CircleShape)
                .clickable { onClick() }
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(horizontal = 24.dp, vertical = 10.dp)
            ) {
                Icon(
                    painter = painterResource(id = data.icon),
                    contentDescription = "",
                    tint = Melrose,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = stringResource(id = data.text),
                    color = Melrose,
                    fontSize = 14.sp
                )
            }
        }
    }
}

@Composable
@Preview
private fun AppOutlineButtonDataPreview() {
   AppTheme {
       Column {
           AppOutlineButton(
               data = AppOutlineButtonData.RecoveryPhone,
               isSelected = true,
               onClick = { }
           )
           Spacer(modifier = Modifier.height(16.dp))
           AppOutlineButton(
               data = AppOutlineButtonData.RecoveryEmail,
               isSelected = false,
               onClick = { }
           )
       }
   }
}