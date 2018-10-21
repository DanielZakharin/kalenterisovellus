package com.daniel.calendarapp.RoomObjects

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import android.arch.persistence.room.OnConflictStrategy.REPLACE
import android.support.annotation.NonNull
import android.support.annotation.Nullable
import org.joda.time.DateTime

/*
Note: Use var for Room objects
 */

@Entity(tableName = "events")
data class Event(

        @ColumnInfo(name = "name")
        @NonNull
        var name: String = "",

        @ColumnInfo(name = "alert_time")
        @Nullable
        var alertTime: DateTime? = null,

        @ColumnInfo(name = "day_id")
        var dayID: Int = 0,

        @ColumnInfo(name = "date")
        var date: DateTime,

        @PrimaryKey(autoGenerate = true)
        @NonNull
        @ColumnInfo(name = "date_id")
        var eventID: Int = 0

) {
    fun withID(id: Int): Event = this.apply {
        eventID = id
    }
}

@Dao
interface EventDao {
    @Query("SELECT * FROM events")
    fun getAll(): LiveData<List<Event>>

    @Query("select * from events where date between :from and :to order by date asc")
    fun getEventsInRange(from: DateTime, to: DateTime): LiveData<List<Event>>

    @Insert(onConflict = REPLACE)
    fun insert(event: Event): Long

    @Query("select * from events where date_id is :id")
    fun getEventWithId(id: Int): LiveData<Event>
}