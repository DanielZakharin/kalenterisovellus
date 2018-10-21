package com.daniel.calendarapp.Utils

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Observer
import kotlinx.coroutines.experimental.CoroutineStart
import kotlinx.coroutines.experimental.Dispatchers
import kotlinx.coroutines.experimental.GlobalScope
import kotlinx.coroutines.experimental.android.Main
import kotlinx.coroutines.experimental.launch
import java.lang.IllegalStateException

/**
 * Livedata for emitting a value once
 *
 * source:
 * https://android.jlelse.eu/android-arch-handling-clicks-and-single-actions-in-your-view-model-with-livedata-ab93d54bc9dc
 */

class ActionLiveData<T> : MutableLiveData<T>() {
    override fun observe(owner: LifecycleOwner, observer: Observer<T>) {
        //limit observers to one at a time
        if (hasObservers()) {
            throw IllegalStateException("ONLY ONE OBSERVER ALLOWED")
        }
        super.observe(owner, Observer { emitted ->
            //ignore nulls
            if(emitted == null) return@Observer
            //if an acutal value was sent, emit it
            observer.onChanged(emitted)
            //set value back to null, so it cannot be retrieved outside the emitted event
            value = null
        })
    }

    fun emit(value: T){
        GlobalScope.launch(Dispatchers.Main, CoroutineStart.DEFAULT, null, {
            this@ActionLiveData.value = value
        })
    }
}