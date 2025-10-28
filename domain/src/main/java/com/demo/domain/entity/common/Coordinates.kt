package com.demo.domain.entity.common

import android.location.Location
import com.demo.common.extensions.round

data class Coordinates(
    val latitude: Float,
    val longitude: Float,
) {

    fun precisionCoordinates(): String {
        return "${this.latitude},${this.longitude}"
    }

    override fun toString(): String {
        return "${this.latitude.round(3)},${this.longitude.round(3)}"
    }

    override fun equals(other: Any?): Boolean {
        if (other !is Coordinates) return false
        return this.latitude == other.latitude && this.longitude == other.longitude
    }

    override fun hashCode(): Int {
        var result = latitude.hashCode()
        result = 31 * result + longitude.hashCode()
        return result
    }

    companion object {

        val defaultCoordinates = Coordinates(0f, 0f)

        fun toCoordinates(lat: Double?, lon: Double?): Coordinates? {
            lat ?: return null
            lon ?: return null
            return Coordinates(lat.toFloat(), lon.toFloat())
        }

        fun Location.toCoordinates() = Coordinates(this.latitude.toFloat(), this.longitude.toFloat())

        fun fromString(string: String): Coordinates {
            val list = string.split(",")
            return Coordinates(
                list[0].toFloat(),
                list[1].toFloat()
            )
        }
    }
}