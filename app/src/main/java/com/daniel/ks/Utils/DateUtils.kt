package com.daniel.ks.Utils

import org.joda.time.LocalTime
import org.joda.time.format.DateTimeFormat
import org.joda.time.format.DateTimeFormatter

private const val DATEFORMAT = "yyyy-MM-ddThh:mm:ss.nnnnnn+|-hh:mm"

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