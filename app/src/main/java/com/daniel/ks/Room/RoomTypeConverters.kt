package com.daniel.ks.Room

import android.arch.persistence.room.TypeConverter
import com.daniel.ks.Utils.toLocalTime
import com.daniel.ks.Utils.toRoomString
import org.joda.time.LocalTime

class RoomTypeConverters {
    companion object {
        @TypeConverter
        @JvmStatic
        fun fromLocalTimeToString(time: LocalTime): String {
            return time.toRoomString()
        }

        @TypeConverter
        @JvmStatic
        fun fromStringToLocalTime(string: String): LocalTime? {
            return string.toLocalTime()
        }
    }
}