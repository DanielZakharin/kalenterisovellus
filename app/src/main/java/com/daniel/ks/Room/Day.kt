package com.daniel.ks.Room

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.support.annotation.NonNull
import org.joda.time.DateTime

@Entity(tableName = "day_table")
data class Day(
        @ColumnInfo(name = "date")
        @NonNull
        val date: DateTime,

        @ColumnInfo(name = "event_id")
        @PrimaryKey(autoGenerate = true)
        val dayID : Int = 0
)