package com.daniel.calendarapp.Utils

import org.joda.time.DateTime
import org.joda.time.LocalTime
import org.joda.time.format.DateTimeFormat
import org.joda.time.format.DateTimeFormatter

private const val DATEFORMAT = "yyyy-MM-ddThh:mm:ss.nnnnnn+|-hh:mm"
private const val READABLEFORMAT_DATE = "dd.MM.yyyy"
private const val READABLEFORMAT_TIME = "dd.MM.yyyy"

fun LocalTime.toRoomString(): String {
    return this.toString(getDateFormatter(DATEFORMAT))
}

fun String.toLocalTime(): LocalTime? {
    return try {
        getDateFormatter(DATEFORMAT).parseLocalTime(this)
    } catch (_:Exception) {
        null
    }
}

fun DateTime.toUIString(): String {
    return this.toString(getDateFormatter(READABLEFORMAT_DATE))
}

fun DateTime.timeToUIString(): String {
    return this.toString(getDateFormatter(READABLEFORMAT_TIME))
}

/*
 * Creating dateFormatters is expensive, so cache them for later use once created
 */

fun getDateFormatter(pattern: String): DateTimeFormatter {
    return CachedDateFormats.get(pattern)
}

private object CachedDateFormats {
    private val formats = HashMap<String, DateTimeFormatter>()
    fun get(pattern: String): DateTimeFormatter {
        val maybeFormat = formats[pattern]
        val format = maybeFormat ?: DateTimeFormat.forPattern(pattern)
        if (maybeFormat == null) formats[pattern] = format
        return format
    }
}