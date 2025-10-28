package com.demo.domain.entity.station

data class Station(
    val id: String?,
    val providerId: String,
    val name: String?,
    val elevation: Int,
    val distance: Float,
)