package com.daniel.ks.CalendarView

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.daniel.ks.R
import com.daniel.ks.Utils.BaseFragment
import com.daniel.ks.databinding.CalendarFragmentBinding

class CalendarFragment : BaseFragment<CalendarFragmentBinding>(R.layout.calendar_fragment) {
    val vm get() = ViewModelProviders.of(this).get(CalendarViewModel::class.java)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding.vm = vm
        return binding.root
    }
}