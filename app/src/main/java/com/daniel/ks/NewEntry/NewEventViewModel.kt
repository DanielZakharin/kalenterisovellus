package com.daniel.ks.NewEntry

import android.app.Application
import android.app.DatePickerDialog
import android.arch.lifecycle.AndroidViewModel
import android.databinding.ObservableField
import android.view.View
import android.widget.DatePicker
import com.daniel.ks.Room.Event
import com.daniel.ks.Utils.ActionLiveData
import org.joda.time.DateTime

class NewEventViewModel(app: Application) : AndroidViewModel(app), DatePickerDialog.OnDateSetListener {
    override fun onDateSet(picker: DatePicker?, year: Int, month: Int, day: Int) {
        date.set(DateTime(year, month + 1, day, 1, 1))
    }

    val title = ObservableField<String>()
    val date = ObservableField<DateTime>(DateTime.now())
    val onNewEvent = ActionLiveData<Event>()
    fun buildEvent(view: View) {
        val e = Event(
                title.get() ?: throw IllegalStateException("NO TITLE PASSED"),
                date = date.get() ?: throw IllegalStateException("NO DATE SUPPLIED")
        )
        onNewEvent.emit(e)
    }
    fun onDateClick(view: View) {
        val n = DateTime.now()
       DatePickerDialog(view.context, this, n.year().get(), n.monthOfYear().get(), n.dayOfMonth).apply {
           datePicker.minDate = DateTime.now().millis
       }.show()
    }
}