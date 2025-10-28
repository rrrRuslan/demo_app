package com.demo.domain.entity.alerts

data class Alert(
    val id: Int,
    val name: String,
    val data: String,
    val dataHtml: String,
    val start: Long,
    val end: Long,
    val impact: AlertImpact,
    val location: String
)

enum class AlertImpact {
    LOW,
    MEDIUM,
    HIGH
}