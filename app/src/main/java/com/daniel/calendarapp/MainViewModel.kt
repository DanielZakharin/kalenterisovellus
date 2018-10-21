package com.daniel.calendarapp

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.content.Context
import com.daniel.calendarapp.RoomObjects.CalendarDatabase

class MainViewModel(app: Application): AndroidViewModel(app){
    lateinit var DB: CalendarDatabase

    fun init(context: Context){
        DB = CalendarDatabase.getInstance(context) ?: throw IllegalStateException("COULD NOT MAKE DATABSE")
    }
}