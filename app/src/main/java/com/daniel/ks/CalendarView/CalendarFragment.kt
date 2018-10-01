package com.daniel.ks.CalendarView

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.MotionEvent
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
        binding.calendarRecyclerView.layoutManager = GridLayoutManager(activity, 7)
        /*binding.calendarRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            private var direction: Int = 0
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                direction = dy
            }

            override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                when (newState) {
                    //SCROLL_STATE_IDLE, SCROLL_STATE_DRAGGING or SCROLL_STATE_SETTLING.
                    RecyclerView.SCROLL_STATE_IDLE -> {
                        if (direction > 0) {
                            vm.loadNextMonth()
                        } else if (direction < 0) {
                            vm.loadPrevMonth()
                        }
                    }
                }
            }
        })*/
        binding.calendarRecyclerView.setOnTouchListener(object : View.OnTouchListener {
            var start = 0f
            var direction = 0
            override fun onTouch(view: View, event: MotionEvent): Boolean {
                when(event.action) {
                    MotionEvent.ACTION_DOWN -> {
                        start = event.y
                        true

                    }
                    MotionEvent.ACTION_MOVE -> {
                        if(event.y > start) direction = -1 else if(event.y < start) direction = 1 else direction = 0
                    }
                    MotionEvent.ACTION_UP -> {
                        if(direction > 0) vm.loadPrevMonth() else if(direction < 0) vm.loadNextMonth()
                    }
                }
                return true
            }

        })
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vm.onShowEventDialog.observe(this, Observer {
            getMainActivity()?.showNewEventDialog(this)
        })
        vm.currentMonth.observe(this, Observer {
            it?.let {
                binding.calendarRecyclerView.adapter = CalendarMonthAdapter(it, binding.calendarRecyclerView.height)
            }
        })
        vm.all.observe(this, Observer {
            it.let(::print)
        })
    }

    override fun onNewEventMade(newEvent: Event) {
        RoomRepo(activity?.application!!).insertEvent(newEvent)
    }
}