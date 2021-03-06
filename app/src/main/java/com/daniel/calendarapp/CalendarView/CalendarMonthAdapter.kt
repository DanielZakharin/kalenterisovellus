package com.daniel.calendarapp.CalendarView

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Build
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.daniel.calendarapp.R
import com.daniel.calendarapp.RoomObjects.Event
import com.daniel.calendarapp.databinding.CalendarMonthCellBinding

interface CalendarMonthClickListener{
    fun onDayClicked(day: Int)
}

class CalendarMonthAdapter(initialEvents: List<Event>, val heightOfRow: Int, val clickHandler: CalendarMonthClickListener) : RecyclerView.Adapter<CalendarMonthAdapter.MonthViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MonthViewHolder {
        return MonthViewHolder(
                DataBindingUtil.inflate(LayoutInflater.from(parent.context), viewType, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return cells.size
    }

    override fun onBindViewHolder(holder: MonthViewHolder, position: Int) {
        holder.itemView.setOnClickListener {
            clickHandler.onDayClicked(position+1)
        }
        delegateBinding(holder, cells[position], position)
    }

    private fun <T : ViewDataBinding> delegateBinding(of: MonthViewHolder, cell: MonthCell<T>, index: Int) = cell.bind(of.binding as T, index)

    override fun getItemViewType(position: Int): Int {
        return cells[position].layoutRes
    }

    var events: List<Event> = emptyList()
        set(value) {
            field = value
            makeCells(value)
        }
    private var cells: List<MonthCell<*>> = emptyList()

    private fun makeCells(events: List<Event>) {
        val tempList = ArrayList<MonthCell<*>>()
        val listByMonths: List<ArrayList<Event>> = List(31) { ArrayList<Event>() }
        val grouped = events.groupBy {
            it.date.dayOfMonth().get()
        }
        for ((day, events) in grouped) {
            listByMonths[day-1].addAll(events) //arrays start at 0, days start at 1. compensate for that
        }
        print("GROUPED: $grouped")
        listByMonths.forEach {
            tempList.add(MonthCellWithEvents(it))
        }
        cells = tempList
        notifyDataSetChanged()
    }

    class MonthViewHolder(val binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root)

    private abstract class MonthCell<in T : ViewDataBinding> {
        abstract val layoutRes: Int
        abstract fun bind(binding: T, index: Int)
    }

    private inner class MonthCellWithEvents(val events: List<Event>) : MonthCell<CalendarMonthCellBinding>() {
        override val layoutRes: Int = R.layout.calendar_month_cell
        override fun bind(binding: CalendarMonthCellBinding, index: Int) {
            binding.root.layoutParams = binding.root.layoutParams.apply {
                height = this@CalendarMonthAdapter.heightOfRow / 5
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                // the view being shared
                binding.root.transitionName = "transition$index"
            }
            binding.titles = events.map {
                it.name
            }
            binding.monthNum = index + 1 //number of the month
        }

    }



    init {
        events = initialEvents
    }
}