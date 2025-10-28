package com.demo.presentation.widget.main.alerts

import androidx.compose.runtime.Immutable
import com.demo.presentation.R

@Immutable
data class AlertItemData(
    val id: Int,
    val text: String,
    val icon: Int
) {
    companion object {
        fun getTestList(): List<AlertItemData> {
            return listOf(
                AlertItemData(0, "Alert 1", R.drawable.ic_alert_blue),
                AlertItemData(0, "Alert 2", R.drawable.ic_alert_orange),
                AlertItemData(0, "Alert 3", R.drawable.ic_alert_red),
            )
        }
    }
}