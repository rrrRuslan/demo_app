package com.demo.presentation.widget.main.mediaContent.data

sealed class MediaType(open val url: String) {
    data class StaticImage(override val url: String) : MediaType(url)
    data class LiveStream(override val url: String): MediaType(url)

    fun toTypeName(): String = when (this) {
        is StaticImage -> IMAGE
        is LiveStream -> LIVE_STREAM
    }

    companion object {
        private const val IMAGE = "image"
        private const val LIVE_STREAM = "live_stream"

        fun fromTypeName(type: String, url: String): MediaType? {
            return when (type) {
                IMAGE -> StaticImage(url)
                LIVE_STREAM -> LiveStream(url)
                else -> null
            }
        }

        fun getSourceType(streamUrl: String?, imageUrl: String?): MediaType? {
            return when {
                streamUrl != null -> LiveStream(streamUrl)
                imageUrl != null -> StaticImage(imageUrl)
                else -> null
            }
        }
    }
}