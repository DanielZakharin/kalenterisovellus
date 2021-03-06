package com.daniel.calendarapp.Utils

import android.app.Activity
import android.util.Log
import android.view.View

private const val TAG = "KALENTERISOVELLUS"

fun <T : Activity> View.getActivity(): T? {
    return context as? T
}

fun log(message: String) {
    Log.v(TAG, message)
}

fun log(t: Throwable) {
    Log.e(TAG, "", t)
}

const val ALARM_REQUEST_CODE = 1234
const val ALARM_EVENT_ID = "ALARM_EVENT_ID"