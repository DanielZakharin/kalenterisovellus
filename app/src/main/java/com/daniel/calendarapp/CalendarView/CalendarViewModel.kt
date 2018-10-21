package com.daniel.calendarapp.CalendarView

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import android.databinding.ObservableField
import android.view.View
import com.daniel.calendarapp.RoomObjects.RoomRepo
import com.daniel.calendarapp.Utils.ActionLiveData
import org.joda.time.DateTime
import java.lang.IllegalStateException

class CalendarViewModel(app: Application) : AndroidViewModel(app) {
    val title = ObservableField<String>()
    private val DB = RoomRepo(app)
    val displayedMonth = MutableLiveData<DateTime>().apply {
        value = DateTime.now()
    }
    val currentMonth = Transformations.switchMap(displayedMonth){
        val from = DateTime(it.year, it.monthOfYear, 1, 0, 0)
        val to = DateTime(it.year, it.monthOfYear, it.dayOfMonth().maximumValue, 23, 59)
        DB.getEventsInRange(from, to)
    }
    val all = DB.allEvents
    val onShowEventDialog = ActionLiveData<Unit>()
    fun showEntryDialog(view: View) {
        onShowEventDialog.emit(Unit)
    }
    fun loadNextMonth() {
        val old = displayedMonth.value ?: throw IllegalStateException("NO MONTH")
        displayedMonth.value = old.minusMonths(1)
    }

    fun loadPrevMonth() {
        val old = displayedMonth.value ?: throw IllegalStateException("NO MONTH")
        displayedMonth.value = old.plusMonths(1)
    }
}