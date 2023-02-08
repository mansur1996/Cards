package com.uz.mycards.utils

import java.math.RoundingMode
import java.text.NumberFormat
import java.util.*

object Utils {
    fun numberFormatterWithWhiteSpace(number: Double, fraction: Int): String {
        val numberFormat = NumberFormat.getNumberInstance(Locale.ENGLISH)
        numberFormat.maximumFractionDigits = fraction
        numberFormat.roundingMode = RoundingMode.CEILING
        val numberFormatter = numberFormat.format(number)
        return numberFormatter.toString().replace("""[,]""".toRegex(), " ")
    }
}