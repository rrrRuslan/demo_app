package com.demo.common.extensions

import java.math.RoundingMode

fun Float.round(to: Int = 3) = toBigDecimal().setScale(to, RoundingMode.UP).toFloat()
