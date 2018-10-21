package com.daniel.calendarapp.CalendarView

import android.app.AlarmManager
import android.app.PendingIntent
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import com.daniel.calendarapp.AlarmView.AlarmActivity
import com.daniel.calendarapp.NewEntry.NewEventReceiver
import com.daniel.calendarapp.R
import com.daniel.calendarapp.RoomObjects.Event
import com.daniel.calendarapp.RoomObjects.RoomRepo
import com.daniel.calendarapp.Utils.ALARM_EVENT_ID
import com.daniel.calendarapp.Utils.ALARM_REQUEST_CODE
import com.daniel.calendarapp.Utils.BaseFragment
import com.daniel.calendarapp.databinding.CalendarFragmentMonthBinding
import com.daniel.calendarapp.databinding.CalendarMonthCellBinding
import org.joda.time.DateTime

class CalendarFragment : BaseFragment<CalendarFragmentMonthBinding>(R.layout.calendar_fragment_month), NewEventReceiver, CalendarMonthClickListener {

    val vm get() = ViewModelProviders.of(this).get(CalendarViewModel::class.java)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding.vm = vm
        //binding.calendarRecyclerView.layoutManager = GridLayoutManager(activity, 7)

        /*val dividerItemDecoration = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
        binding.calendarRecyclerView.addItemDecoration(dividerItemDecoration)
        binding.calendarRecyclerView.adapter = CalendarMonthAdapter(
                vm.currentMonth?.value ?: emptyList(),
                binding.calendarRecyclerView.height,
                this)*/
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vm.onShowEventDialog.observe(this, Observer {
            getMainActivity()?.showNewEventDialog(this)
        })
        vm.currentMonth.observe(this, Observer {
            it?.let {
                //binding.calendarRecyclerView.adapter = CalendarMonthAdapter(it, binding.calendarRecyclerView.height, this)
                populateGrid(it)
            }
        })
        vm.all.observe(this, Observer {
            it.let(::print)
        })
    }

    private fun populateGrid(list: List<Event>) {
        val listByMonths: List<ArrayList<Event>> = List(31) { ArrayList<Event>() }
        val grouped = list.groupBy {
            it.date.dayOfMonth().get()
        }
        for ((day, events) in grouped) {
            listByMonths[day-1].addAll(events) //arrays start at 0, days start at 1. compensate for that
        }
        val inflater = LayoutInflater.from(activity)
        binding.gridLayout.removeAllViews()
        for ((index,day) in listByMonths.withIndex()) {
            val cell = DataBindingUtil.inflate<CalendarMonthCellBinding>(inflater, R.layout.calendar_month_cell, binding.gridLayout, true)
            cell.monthNum = index + 1
            cell.titles = day.map {
                it.name
            }
            cell.root.layoutParams = (cell.root.layoutParams as? GridLayout.LayoutParams)?.apply {
                height = 0
                width = 0
                columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f)
                rowSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f)
            }
            cell.root.setOnClickListener {
                onDayClicked(index)
            }
        }
    }

    /*
     * New event dialog returned an event
     */
    override fun onNewEventMade(newEvent: Event) {
        RoomRepo(activity?.application!!).insertEvent(newEvent) {
            /*
             * After inserting a new event into database, 'it' is the id of the new event
             */
            scheduleAlarm(
                    newEvent.withID(it.toInt())
            )
        }
    }

    private fun scheduleAlarm(event: Event) {
        /*
         * Get systems AlarmManager
         */
        (activity?.getSystemService(Context.ALARM_SERVICE) as? AlarmManager)?.let { alarmManager ->
            /*
             * Create the intent that will be launched once alarm is triggered
             * Add event ID as an extra to the intent
             * Give error if no alert time is specified
             */
            val alarmIntent = Intent(context, AlarmActivity::class.java)
            alarmIntent.putExtra(ALARM_EVENT_ID, event.eventID)
            val pendingIntent = PendingIntent.getActivity(context, ALARM_REQUEST_CODE, alarmIntent, 0)
            alarmManager.set(
                    AlarmManager.RTC_WAKEUP,
                    event.alertTime?.millis ?: DateTime.now().millis + 1000 ?: throw java.lang.IllegalStateException("NO ALERT TIME GIVEN"),
                    pendingIntent)
        }
    }

    override fun onDayClicked(day: Int) {
        //switch to day view
        getMainActivity()?.showFragment(DayFragment(vm.displayedMonth.value ?: throw IllegalStateException("NO ID FOR CLICKED EVENT")))
    }

    override fun onResume() {
        super.onResume()
        vm.currentMonth?.value?.let {
            populateGrid(it)
        }
    }
}