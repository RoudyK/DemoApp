package com.demo.roudykk.demoapp.analytics.mixpanel

import android.content.Context
import com.demo.roudykk.demoapp.analytics.base.EventBundle
import com.demo.roudykk.demoapp.analytics.base.Tracker
import com.demo.roudykk.demoapp.analytics.consts.Event
import com.demo.roudykk.demoapp.extensions.toJSONObject

class MixpanelTracker(private val context: Context) : Tracker {

    override fun track(event: Event, bundle: EventBundle) {
        Mixpanel.getInstance(context)?.track(event.value, bundle.toBundle().toJSONObject())
    }

}