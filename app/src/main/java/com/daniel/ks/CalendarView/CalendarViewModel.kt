package com.daniel.ks.CalendarView

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.Transformations
import android.databinding.ObservableField
import com.daniel.ks.Room.Event
import com.daniel.ks.Room.RoomRepo
import com.daniel.ks.Utils.log
import org.joda.time.DateTime

class CalendarViewModel(app: Application) : AndroidViewModel(app) {
    val title = ObservableField<String>()
    val DB = RoomRepo(app)
    val all by lazy { DB.allEvents }
    val allCount by lazy {
        Transformations.map(all) {
            it.size
        }
    }

    fun makeEntry() {
        log("Pressed make")
        DB.insertEvent(Event(
                "this is test",
                DateTime(DateTime.now().millis + 900000)
        ))
    }

    fun getAll() {
        log("Pressed get")
    }
}