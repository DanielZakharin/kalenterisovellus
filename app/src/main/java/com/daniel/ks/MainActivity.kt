package com.daniel.ks

import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Build
import android.os.Bundle
import android.support.transition.TransitionInflater
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import com.daniel.ks.CalendarView.CalendarFragment
import com.daniel.ks.NewEntry.NewEventDialogFragment
import com.daniel.ks.databinding.ActivityMainBinding

private const val REQUEST_CODE = 9001

class MainActivity : AppCompatActivity() {

    private val vm get() = ViewModelProviders.of(this).get(MainViewModel::class.java)

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        vm.init(this)
        showFragment(CalendarFragment(), addToStack = false)
    }

    fun showFragment(fragment: Fragment, target: Fragment? = null, addToStack: Boolean = true) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            supportFragmentManager.findFragmentById(R.id.fragment_container)?.let { current ->
                current.sharedElementReturnTransition = TransitionInflater.from(this).inflateTransition(R.transition.default_transition)
                current.exitTransition = TransitionInflater.from(this).inflateTransition(android.R.transition.no_transition)

                fragment.sharedElementEnterTransition = TransitionInflater.from(this).inflateTransition(R.transition.default_transition)
                fragment.enterTransition = TransitionInflater.from(this).inflateTransition(android.R.transition.no_transition)
            }
        }
        target?.let { fragment.setTargetFragment(it, REQUEST_CODE) }
        supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragment_container, fragment, "something")
                .also {
                    if (addToStack) it.addToBackStack(fragment.javaClass.canonicalName)
                }
                .commit()
    }

    fun showNewEventDialog(target: Fragment) {
        NewEventDialogFragment().apply {
            setTargetFragment(target, REQUEST_CODE)
            show(supportFragmentManager, "TAG")
        }
    }
}
