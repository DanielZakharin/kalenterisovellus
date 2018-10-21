package com.daniel.calendarapp

import android.os.Bundle
import android.support.v4.app.Fragment
import com.daniel.calendarapp.CalendarView.CalendarFragment
import com.daniel.calendarapp.NewEntry.NewEventDialogFragment
import com.daniel.calendarapp.Utils.BaseActivity
import com.daniel.calendarapp.databinding.ActivityMainBinding

private const val REQUEST_CODE = 9001

class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        showFragment(CalendarFragment(), addToStack = false)
    }

    fun showFragment(fragment: Fragment, target: Fragment? = null, addToStack: Boolean = true) {
        target?.let { fragment.setTargetFragment(it, REQUEST_CODE) }
        supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragment_container, fragment, "something")
                .also {
                    if (addToStack) {
                        it.addToBackStack(fragment.javaClass.canonicalName)
                    }
                }
                .commit()
    }

    fun showNewEventDialog(target: Fragment) {
        NewEventDialogFragment().apply {
            setTargetFragment(target, REQUEST_CODE)
            show(supportFragmentManager, "TAG")
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }
}
