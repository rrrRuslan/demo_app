package com.demo.common.extensions

fun String.extractNumbers(): List<Int> {
    val regex = Regex("\\d+")
    val matches = regex.findAll(this)

    return matches.map { it.value.toInt() }.toList()
}