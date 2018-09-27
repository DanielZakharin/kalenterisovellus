package com.daniel.ks.Utils

import android.databinding.BindingAdapter
import android.widget.TextView
import org.joda.time.DateTime

@BindingAdapter("text_date")
fun setDateText(textView: TextView, date: DateTime) {
    textView.text = date.toUIString()
}