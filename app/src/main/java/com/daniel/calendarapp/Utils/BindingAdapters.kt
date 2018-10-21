package com.daniel.calendarapp.Utils

import android.databinding.BindingAdapter
import android.view.View
import android.widget.TextView
import org.joda.time.DateTime

@BindingAdapter("text_date")
fun setDateText(textView: TextView, date: DateTime) {
    textView.text = date.toUIString()
}

@BindingAdapter("visible")
fun setVisible(view: View, visible: Boolean?) {
    view.visibility = if(visible == true) View.VISIBLE else View.GONE
}

@BindingAdapter("text_any")
fun setTextAny(tv: TextView, any: Any?) {
    tv.text = any?.toString() ?: ""
}