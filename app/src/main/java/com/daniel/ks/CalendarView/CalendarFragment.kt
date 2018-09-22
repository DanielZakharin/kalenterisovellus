package com.daniel.ks.CalendarView

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.daniel.ks.NewEntry.NewEventReceiver
import com.daniel.ks.R
import com.daniel.ks.Room.Event
import com.daniel.ks.Room.RoomRepo
import com.daniel.ks.Utils.BaseFragment
import com.daniel.ks.databinding.CalendarFragmentBinding

class CalendarFragment : BaseFragment<CalendarFragmentBinding>(R.layout.calendar_fragment), NewEventReceiver {
    val vm get() = ViewModelProviders.of(this).get(CalendarViewModel::class.java)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding.vm = vm
        binding.calendarRecyclerView.layoutManager = GridLayoutManager(activity, 6)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vm.onShowEventDialog.observe(this, Observer {
            getMainActivity()?.showNewEventDialog(this)
        })
        vm.all.observe(this, Observer {
            it?.let {
                binding.calendarRecyclerView.adapter = CalendarMonthAdapter(it)
            }
        })
    }

    override fun onNewEventMade(newEvent: Event) {
        RoomRepo(activity?.application!!).insertEvent(newEvent)
    }
}