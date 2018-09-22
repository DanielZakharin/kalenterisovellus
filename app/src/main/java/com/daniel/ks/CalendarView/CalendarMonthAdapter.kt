package com.daniel.ks.CalendarView

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.daniel.ks.R
import com.daniel.ks.Room.Event
import com.daniel.ks.databinding.CalendarMonthCellBinding

class CalendarMonthAdapter(initialEvents: List<Event>): RecyclerView.Adapter<CalendarMonthAdapter.MonthViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MonthViewHolder {
        return MonthViewHolder(
               DataBindingUtil.inflate(LayoutInflater.from(parent.context), viewType, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return cells.size
    }

    override fun onBindViewHolder(holder: MonthViewHolder, position: Int) {
        delegateBinding(holder, cells[position])
    }

    private fun <T: ViewDataBinding>delegateBinding(of: MonthViewHolder, cell: MonthCell<T>) = cell.bind(of.binding as T)

    override fun getItemViewType(position: Int): Int {
        return cells[position].layoutRes
    }

    var events: List<Event> = emptyList()
    set(value) {
        field = value
        makeCells(value)
    }
    private var cells: List<MonthCell<*>> =  emptyList()

    private fun makeCells(events: List<Event>) {
        val tempList = ArrayList<MonthCell<*>>()
        events.forEach {
            tempList.add(MonthCellWithEvent(it))
        }
        cells = tempList
        notifyDataSetChanged()
    }

    class MonthViewHolder(val binding: ViewDataBinding): RecyclerView.ViewHolder(binding.root)

    private abstract class MonthCell<in T: ViewDataBinding>{
        abstract val layoutRes: Int
        abstract fun bind(binding: T)
    }

    private class MonthCellWithEvent(val event: Event): MonthCell<CalendarMonthCellBinding>(){
        override val layoutRes: Int = R.layout.calendar_month_cell
        override fun bind(binding: CalendarMonthCellBinding) {
            binding.t = event.name
        }

    }

    init {
        events = initialEvents
    }
}