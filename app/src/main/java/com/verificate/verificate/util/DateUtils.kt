package com.verificate.verificate.util

import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*


fun getPatternFromDate(date: String, outputPattern: String = "dd MMM, yyyy", inputPattern:String = "yyyy-MM-dd") : String{
    val originalFormat: DateFormat = SimpleDateFormat(inputPattern, Locale.ENGLISH)
    val targetFormat = SimpleDateFormat(outputPattern)
    val formattedDate = originalFormat.parse(date)
    return targetFormat.format(formattedDate)
}