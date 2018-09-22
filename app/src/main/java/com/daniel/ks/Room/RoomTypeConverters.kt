package com.daniel.ks.Room

import android.arch.persistence.room.TypeConverter
import com.daniel.ks.Utils.toLocalTime
import com.daniel.ks.Utils.toRoomString
import org.joda.time.DateTime
import org.joda.time.LocalTime

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