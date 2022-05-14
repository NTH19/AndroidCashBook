package com.example.cashbook.utils

import java.math.BigDecimal


object FloatUtils {
    fun div(v1: Float, v2: Float): Float {
        val v3 = v1 / v2
        val b1 = BigDecimal(v3.toDouble())
        return b1.setScale(4, 4).toFloat()
    }

    fun ratioToPercent(`val`: Float): String {
        val v = `val` * 100
        val b1 = BigDecimal(v.toString())
        val v1 = b1.setScale(2, 4).toFloat()
        return "$v1%"
    }
}
