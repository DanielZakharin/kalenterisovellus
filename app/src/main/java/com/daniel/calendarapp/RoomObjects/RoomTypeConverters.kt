package com.daniel.calendarapp.RoomObjects

import android.arch.persistence.room.TypeConverter
import org.joda.time.DateTime

class RoomTypeConverters {
    companion object {
        @TypeConverter
        @JvmStatic
        fun fromLocalTimeToString(time: DateTime?): Long? {
            return time?.millis ?: null
        }

        @TypeConverter
        @JvmStatic
        fun fromStringToLocalTime(long: Long?): DateTime? {
            return DateTime(long)
        }
    }
}