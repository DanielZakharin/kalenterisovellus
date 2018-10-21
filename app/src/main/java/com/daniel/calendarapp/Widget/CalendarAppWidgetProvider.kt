package com.daniel.calendarapp.Widget

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import android.widget.Toast
import com.daniel.calendarapp.R

class CalendarAppWidgetProvider : AppWidgetProvider() {
    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager?, appWidgetIds: IntArray?) {
        super.onUpdate(context, appWidgetManager, appWidgetIds)
        appWidgetIds?.forEach { id ->
            val remotes = RemoteViews(context.packageName, R.layout.calendar_widget)
            remotes.setOnClickPendingIntent(R.id.widget_add_button, makePendingSelfIntent(context, Actions.NEW))
            appWidgetManager?.updateAppWidget(id, remotes)
        }
    }

    private enum class Actions {
        NEW,
        OPEN
    }

    private fun String?.equalsAction(action: Actions): Boolean {
        return this == action.name
    }

    private fun makePendingSelfIntent(context: Context, actionId: Actions) : PendingIntent{
        val intent = Intent(context, javaClass)
        intent.action = actionId.name
        return PendingIntent.getBroadcast(context, 0, intent, 0)
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        super.onReceive(context, intent)
        intent ?: return
        when {
            intent.action.equalsAction(Actions.NEW) -> {
                Toast.makeText(context, "CLICKED NEW", Toast.LENGTH_SHORT).show()
            }
            intent.action.equalsAction(Actions.OPEN) -> {
                Toast.makeText(context, "CLICKED OPEN", Toast.LENGTH_SHORT).show()
            }
        }
    }
}