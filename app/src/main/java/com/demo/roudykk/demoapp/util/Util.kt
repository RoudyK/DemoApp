package com.demo.roudykk.demoapp.util

import android.annotation.SuppressLint
import com.airbnb.epoxy.EpoxyController
import com.airbnb.epoxy.EpoxyRecyclerView
import java.text.SimpleDateFormat
import java.util.*

const val NOW_TIME = 10000L
const val MiNS_AGO_TIME = 5 * 60 * 1000L


fun Long.displayDate(): String {
    val calendar = Calendar.getInstance()
    calendar.timeInMillis = this

    val todayCalendar = Calendar.getInstance()

    val year = calendar.year()
    val month = calendar.month()
    val day = calendar.day()

    val curYear = todayCalendar.year()
    val curMonth = todayCalendar.month()
    val curDay = todayCalendar.day()

    if (todayCalendar.timeInMillis - this <= NOW_TIME) {
        return "Just now"
    }

    if (todayCalendar.timeInMillis - this <= MiNS_AGO_TIME) {
        return "A few mins ago"
    }

    val date: String

    date = if (year == curYear) {
        if (month == curMonth) {
            when (day) {
                curDay -> "Today"
                (curDay - 1) -> "Yesterday"
                else -> "${calendar.displayDay()}, ${calendar.day()}"
            }
        } else {
            "${calendar.displayDay()}, ${calendar.day()}, ${calendar.displayMonth()}"
        }
    } else {
        "${calendar.displayDay()}, ${calendar.day()}, ${calendar.displayMonth()}, ${calendar.year()}"
    }

    return "$date at ${calendar.displayTime()}"
}

@SuppressLint("SimpleDateFormat")
private fun Calendar.displayTime(): String {
    return SimpleDateFormat("HH:mm a").format(time)
}

private fun Calendar.displayDay(): String {
    return getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault())
}

private fun Calendar.displayMonth(): String {
    return getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault())
}

private fun Calendar.year(): Int {
    return get(Calendar.YEAR)
}

private fun Calendar.month(): Int {
    return get(Calendar.MONTH)
}

private fun Calendar.day(): Int {
    return get(Calendar.DAY_OF_MONTH)
}

fun EpoxyRecyclerView.withModels(buildModelsCallback: EpoxyController.() -> Unit) {
    setControllerAndBuildModels(object : EpoxyController() {
        override fun buildModels() {
            buildModelsCallback()
        }
    })
}