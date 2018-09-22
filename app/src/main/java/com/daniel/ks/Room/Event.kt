package com.daniel.ks.Room

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import android.arch.persistence.room.OnConflictStrategy.REPLACE
import android.support.annotation.NonNull
import android.support.annotation.Nullable
import org.joda.time.DateTime
import org.joda.time.LocalTime

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

        @PrimaryKey(autoGenerate = true)
        @NonNull
        @ColumnInfo(name = "event_id")
        var eventID: Int = 0
)

@Dao
interface EventDao {
    @Query("SELECT * FROM events")
    fun getAll(): LiveData<List<Event>>

    @Insert(onConflict = REPLACE)
    fun insert(event: Event)
}