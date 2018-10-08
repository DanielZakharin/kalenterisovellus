package com.daniel.ks.Room

import android.app.Application
import kotlinx.coroutines.experimental.CoroutineStart
import kotlinx.coroutines.experimental.Dispatchers
import kotlinx.coroutines.experimental.GlobalScope
import kotlinx.coroutines.experimental.launch
import org.joda.time.DateTime

class RoomRepo(app: Application) {
    val db = CalendarDatabase.getInstance(app) ?: throw NullPointerException("COULD NOT MAKE DB")
    private val eventDao = db.eventDao()
    val allEvents by lazy { eventDao.getAll() }
    fun getEventsInRange(from: DateTime, to: DateTime) = eventDao.getEventsInRange(from, to)

    fun insertEvent(newEvent: Event, completion: (Long) -> Unit) {
        GlobalScope.launch(Dispatchers.Default, CoroutineStart.DEFAULT, block = {
            completion(insertEvent(newEvent))
        })
    }
    private fun insertEvent(event: Event): Long {
        return eventDao.insert(event)
    }
}