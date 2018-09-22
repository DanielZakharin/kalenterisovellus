package com.daniel.ks.NewEntry

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.databinding.ObservableField
import android.view.View
import com.daniel.ks.Room.Event
import com.daniel.ks.Utils.ActionLiveData

class NewEventViewModel(app: Application) : AndroidViewModel(app) {
    val title = ObservableField<String>()

    val onNewEvent = ActionLiveData<Event>()
    fun buildEvent(view: View) {
        onNewEvent.emit(
                Event(
                        title.get() ?: throw IllegalStateException("NO TITLE PASSED")
                )
        )
    }
}