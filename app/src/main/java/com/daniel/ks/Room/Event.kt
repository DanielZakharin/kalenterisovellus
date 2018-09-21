package com.daniel.ks.Room

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import android.arch.persistence.room.OnConflictStrategy.REPLACE
import android.support.annotation.NonNull
import org.joda.time.DateTime
import org.joda.time.LocalTime

@Entity(tableName = "events")
data class Event(

        @ColumnInfo(name = "name")
        @NonNull
        val name: String = "",

        @ColumnInfo(name = "alert_time")
        @NonNull
        val alertTime: DateTime,

        @PrimaryKey(autoGenerate = true)
        @NonNull
        @ColumnInfo(name = "event_id")
        val eventID: Int = 0
)

@Dao
interface EventDao {
    @Query("SELECT * FROM events")
    fun getAll(): LiveData<List<Event>>

    @Insert(onConflict = REPLACE)
    fun insert(event: Event)
}