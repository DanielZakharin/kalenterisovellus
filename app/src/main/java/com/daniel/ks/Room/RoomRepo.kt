package com.daniel.ks.Room

import android.app.Application
import kotlinx.coroutines.experimental.CoroutineStart
import kotlinx.coroutines.experimental.Dispatchers
import kotlinx.coroutines.experimental.GlobalScope
import kotlinx.coroutines.experimental.launch

class RoomRepo(app: Application) {
    val db = CalendarDatabase.getInstance(app) ?: throw NullPointerException("COULD NOT MAKE DB")
    private val eventDao = db.eventDao()
    val allEvents by lazy {  eventDao.getAll() }

    fun insertEvent(event: Event) {
        GlobalScope.launch(Dispatchers.Default, CoroutineStart.DEFAULT, block = {
            eventDao.insert(event)
        })
    }
}