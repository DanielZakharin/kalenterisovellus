package com.daniel.ks.CalendarView

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.daniel.ks.NewEntry.NewEventReceiver
import com.daniel.ks.R
import com.daniel.ks.Room.Event
import com.daniel.ks.Room.RoomRepo
import com.daniel.ks.Utils.BaseFragment
import com.daniel.ks.databinding.CalendarFragmentMonthBinding

class CalendarFragment : BaseFragment<CalendarFragmentMonthBinding>(R.layout.calendar_fragment_month), NewEventReceiver, CalendarMonthClickListener {

    val vm get() = ViewModelProviders.of(this).get(CalendarViewModel::class.java)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding.vm = vm
        binding.calendarRecyclerView.layoutManager = GridLayoutManager(activity, 7)

        val dividerItemDecoration = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
        binding.calendarRecyclerView.addItemDecoration(dividerItemDecoration)
        binding.calendarRecyclerView.adapter = CalendarMonthAdapter(
                vm.currentMonth?.value ?: emptyList(),
                binding.calendarRecyclerView.height,
                this)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vm.onShowEventDialog.observe(this, Observer {
            getMainActivity()?.showNewEventDialog(this)
        })
        vm.currentMonth.observe(this, Observer {
            it?.let {
                binding.calendarRecyclerView.adapter = CalendarMonthAdapter(it, binding.calendarRecyclerView.height, this)
            }
        })
        vm.all.observe(this, Observer {
            it.let(::print)
        })
    }

    override fun onNewEventMade(newEvent: Event) {
        RoomRepo(activity?.application!!).insertEvent(newEvent)
    }

    override fun onDayClicked(day: Int) {
        //switch to day view
        getMainActivity()?.showFragment(DayFragment(vm.displayedMonth.value ?: throw IllegalStateException("NO ID FOR CLICKED EVENT")))
    }
}