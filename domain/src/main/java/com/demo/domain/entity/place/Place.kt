package com.demo.domain.entity.place

import com.demo.domain.entity.common.Coordinates

data class Place(
    val id: Long,
    val countryName: String,
    val coordinates: Coordinates,
    val location: String,
    val stateAbbr: String,
    val state: String,
    val zoneId: String,
    val zip: String? = null,
    val addTime: Long? = null,
)