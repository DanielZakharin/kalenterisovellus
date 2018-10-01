package com.daniel.ks.Utils

import android.app.Activity
import android.content.Context
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