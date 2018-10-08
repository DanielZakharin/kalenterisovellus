package com.daniel.ks.Utils

import android.app.Activity
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.daniel.ks.MainViewModel
import com.daniel.ks.R
import com.daniel.ks.databinding.CalendarMonthCellBinding

abstract class BaseActivity<T: ViewDataBinding>(val layoutId: Int): AppCompatActivity(){
    lateinit var binding: T
    val vm get() = ViewModelProviders.of(this).get(MainViewModel::class.java)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vm.init(this)
        binding = DataBindingUtil.setContentView(this, layoutId)
    }
}