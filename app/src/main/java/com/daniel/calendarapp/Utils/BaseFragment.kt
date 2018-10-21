package com.daniel.calendarapp.Utils

import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.daniel.calendarapp.MainActivity
import com.daniel.calendarapp.MainViewModel
import java.lang.IllegalStateException

/**
 * Base fragment that handles creating a databinding and sets lifecycle owners properly
 */

abstract class BaseFragment<T: ViewDataBinding>(@LayoutRes val layout: Int) : Fragment(){
    protected lateinit var binding: T
    val mvm by lazy {
        getMainActivity()?.let {
            ViewModelProviders.of(it).get(MainViewModel::class.java)
        } ?: throw IllegalStateException("NO ACTIVITY")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, layout, container, false)
        return binding.root
    }

    /*
     * Set the fragment as lifecycleowner, so that the binding will be garbage collected properly
     * Livedata also does not cooperate if the lifecycleowner has not been set
     */

    override fun onStart() {
        super.onStart()
        binding.setLifecycleOwner(this)
    }

    override fun onStop() {
        super.onStop()
        binding.setLifecycleOwner(null)
    }

    fun getMainActivity(): MainActivity? = activity as? MainActivity
}