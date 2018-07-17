package com.demo.roudykk.demoapp.analytics.base

import com.demo.roudykk.demoapp.analytics.consts.Event

interface Tracker {

    fun track(event: Event, bundle: EventBundle)
}