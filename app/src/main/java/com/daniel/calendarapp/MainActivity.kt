package com.daniel.calendarapp

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.widget.Toast
import com.daniel.calendarapp.CalendarView.CalendarFragment
import com.daniel.calendarapp.NewEntry.NewEventDialogFragment
import com.daniel.calendarapp.Utils.BaseActivity
import com.daniel.calendarapp.databinding.ActivityMainBinding

private const val REQUEST_CODE = 9001

enum class Intents {
    ALARM,
    NEW_EVENT
}

const val INTENT_EXTRA = "INTENT_EXTRA"

class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        showFragment(CalendarFragment(), addToStack = false)?.let {
            handleIntentExtra(intent, it)
        }
    }

    fun showFragment(fragment: Fragment, target: Fragment? = null, addToStack: Boolean = true): Fragment {
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
        return fragment
    }

    fun handleIntentExtra(intent: Intent?, target: Fragment) {
        when (intent?.getSerializableExtra(INTENT_EXTRA) as? Intents) {
            Intents.ALARM -> { /*TODO convert activity to fragment & show */
            }
            Intents.NEW_EVENT -> {
                showNewEventDialog(target)
            }
        }
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
