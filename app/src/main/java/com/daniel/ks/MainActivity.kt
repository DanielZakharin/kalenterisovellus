package com.daniel.ks

import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import com.daniel.ks.CalendarView.CalendarFragment
import com.daniel.ks.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val vm get() = ViewModelProviders.of(this).get(MainViewModel::class.java)

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        vm.init(this)
        showFragment(CalendarFragment())
    }

    fun showFragment(fragment: Fragment) {
        supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(fragment.javaClass.name)
                .commit()
    }
}
