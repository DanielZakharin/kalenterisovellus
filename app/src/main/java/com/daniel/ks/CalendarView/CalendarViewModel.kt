package com.daniel.ks.CalendarView

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.Transformations
import android.databinding.ObservableField
import android.view.View
import com.daniel.ks.Room.RoomRepo
import com.daniel.ks.Utils.ActionLiveData

class CalendarViewModel(app: Application) : AndroidViewModel(app) {
    val title = ObservableField<String>()
    private val DB = RoomRepo(app)
    val all by lazy { DB.allEvents }
    val allCount by lazy {
        Transformations.map(all) {
            it.joinToString {
                it.name
            }
        }
    }

    val onShowEventDialog = ActionLiveData<Unit>()
    fun showEntryDialog(view: View) {
        onShowEventDialog.emit(Unit)
    }

    fun getAll() {
        print("Pressed get")
    }
}