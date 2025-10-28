package com.demo.domain.entity.skyCams

data class SkyCams(
    val id: Int,
    val imageUrl: String?,
    val stream: String?,
    val name: String?,
    val location: String?,
    val lastUpdateTime: Long?,
    val state: String?
)