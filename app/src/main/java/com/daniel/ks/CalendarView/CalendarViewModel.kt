package com.daniel.ks.CalendarView

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import android.databinding.ObservableField
import android.view.View
import com.daniel.ks.Room.RoomRepo
import com.daniel.ks.Utils.ActionLiveData
import org.joda.time.DateTime

class CalendarViewModel(app: Application) : AndroidViewModel(app) {
    val title = ObservableField<String>()
    private val DB = RoomRepo(app)
    val all by lazy { DB.allEvents }
    val displayedMonth = MutableLiveData<DateTime>().apply {
        value = DateTime.now()
    }
    val currentMonth = Transformations.switchMap(displayedMonth){
        val from = DateTime(it.year, it.monthOfYear, 1, 0, 0)
        val to = DateTime(it.year, it.monthOfYear, 30, 23, 59)
        DB.getEventsInRange(from, to)
    }

    val onShowEventDialog = ActionLiveData<Unit>()
    fun showEntryDialog(view: View) {
        onShowEventDialog.emit(Unit)
    }
}