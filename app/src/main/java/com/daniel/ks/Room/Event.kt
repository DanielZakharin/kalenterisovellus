package com.daniel.ks.Room

import android.arch.persistence.room.*
import android.arch.persistence.room.OnConflictStrategy.REPLACE
import android.support.annotation.NonNull
import org.joda.time.LocalTime

@Entity(tableName = "events")
data class Event(
        @PrimaryKey
        @NonNull
        @ColumnInfo(name = "event_id")
        val eventID: String = "",

        @ColumnInfo(name = "name")
        @NonNull
        val name: String = "",

        @ColumnInfo(name = "alert_time")
        @NonNull
        val alertTime: LocalTime
)

@Dao
interface EventDao {
    @Query("SELECT * FROM events")
    fun getAll(): List<Event>

    @Insert(onConflict = REPLACE)
    fun insert(event: Event)
}