package com.demo.roudykk.demoapp.util.extensions

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

const val SECS_TO_MILLS = 1000L
const val SECS_IN_MINS = 60
const val NOW_TIME = 10 * SECS_TO_MILLS
const val SECONDS_AGO_TIME = 1 * SECS_IN_MINS * SECS_TO_MILLS
const val MiNS_AGO_TIME = 5 * SECS_IN_MINS * SECS_TO_MILLS

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

    if (todayCalendar.timeInMillis - this <= SECONDS_AGO_TIME) {
        return "A few seconds ago"
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

fun Calendar.year(): Int {
    return get(Calendar.YEAR)
}

private fun Calendar.month(): Int {
    return get(Calendar.MONTH)
}

private fun Calendar.day(): Int {
    return get(Calendar.DAY_OF_MONTH)
}
