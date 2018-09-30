package com.daniel.ks.NewEntry

import android.app.Dialog
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import com.daniel.ks.R
import com.daniel.ks.Room.Event
import com.daniel.ks.databinding.NewEventDialogBinding
import org.joda.time.DateTime
import java.lang.IllegalStateException

interface NewEventReceiver {
    fun onNewEventMade(newEvent: Event)
}

class NewEventDialogFragment : DialogFragment() {

    lateinit var binding: NewEventDialogBinding
    val vm get() = ViewModelProviders.of(this).get(NewEventViewModel::class.java)

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        vm.onNewEvent.observe(this, Observer { event ->
            (targetFragment as? NewEventReceiver)?.let {
                it.onNewEventMade(event!!)
            } ?: throw IllegalStateException("NO TARGET FOR NEW EVENT")
        })
        binding = makeBinding()
        return android.support.v7.app.AlertDialog.Builder(context!!)
                .setView(binding.root)
                .setCancelable(false)
                .create()
    }

    private fun makeBinding(): NewEventDialogBinding {
        val li = LayoutInflater.from(activity)
        return DataBindingUtil.inflate<NewEventDialogBinding>(li, R.layout.new_event_dialog, null, false).apply { vm = this@NewEventDialogFragment.vm }
    }


}