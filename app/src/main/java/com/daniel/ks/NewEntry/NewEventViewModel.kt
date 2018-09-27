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
    override fun onDateSet(p0: DatePicker?, year: Int, month: Int, day: Int) {
        date.set(DateTime(year, month, day, 1, 1))
    }

    val title = ObservableField<String>()
    val date = ObservableField<DateTime>(DateTime.now())
    val onNewEvent = ActionLiveData<Event>()
    fun buildEvent(view: View) {
        onNewEvent.emit(
                Event(
                        title.get() ?: throw IllegalStateException("NO TITLE PASSED")
                )
        )
    }
    fun onDateClick(view: View) {
       DatePickerDialog(view.context, this, 2018, 0, 1).apply {
           datePicker.minDate = DateTime.now().millis
       }.show()
    }
}