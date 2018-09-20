package com.daniel.ks.Room

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import android.content.Context

private const val DB_NAME = "calendar_db"

@Database(entities = [Event::class], version = 1)
@TypeConverters(RoomTypeConverters::class)
abstract class CalendarDatabase: RoomDatabase() {
    abstract fun eventDao(): EventDao

    companion object {
        private var INSTANCE: CalendarDatabase? = null

        @Synchronized
        fun getInstance(context: Context): CalendarDatabase? {
            if(INSTANCE == null) {
                synchronized(CalendarDatabase::class) {
                    INSTANCE = Room.databaseBuilder(context, CalendarDatabase::class.java, DB_NAME).build()
                }
            }
            return INSTANCE
        }
    }
}