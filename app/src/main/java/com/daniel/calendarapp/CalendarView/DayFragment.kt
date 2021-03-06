package com.daniel.calendarapp.CalendarView

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.daniel.calendarapp.R
import com.daniel.calendarapp.Utils.BaseFragment
import com.daniel.calendarapp.databinding.CalendarFragmentDayBinding
import org.joda.time.DateTime

private const val ARG_ID = "ARG_ID"

fun DayFragment(eventDate: DateTime) : DayFragment {
    return DayFragment().apply {
        val bundle = Bundle()
        bundle.putSerializable(ARG_ID, eventDate)
        arguments = bundle
    }
}

class DayFragment:BaseFragment<CalendarFragmentDayBinding>(R.layout.calendar_fragment_day) {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return binding.root
    }
}

class CalendarDayAdapter{


    private class CellHolder()
}