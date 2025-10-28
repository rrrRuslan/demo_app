package com.demo.common.utils

sealed class ErrorModel(open val message: String) {

    data class Error(override val message: String) : ErrorModel(message)
    data class ErrorData<T>(
        override val message: String,
        val data: T
    ) : ErrorModel(message = "")

    object NoConnection : ErrorModel("No connection")

    companion object {
        fun create(message: String) = Error(message)
        fun unknown() = Error("Unknown error")
    }

}
