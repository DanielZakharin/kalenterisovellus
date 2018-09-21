package com.daniel.ks.Utils

import android.util.Log

private const val TAG = "KALENTERISOVELLUS"

fun log(message: String) {
    Log.v(TAG, message)
}

fun log(t: Throwable) {
    Log.e(TAG, "", t)
}