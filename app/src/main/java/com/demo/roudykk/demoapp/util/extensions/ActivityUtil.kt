package com.demo.roudykk.demoapp.util.extensions

import android.support.v7.app.AppCompatActivity
import com.demo.roudykk.demoapp.util.UiJob
import java.util.*

fun AppCompatActivity.scheduleOnUi(uiJob: UiJob, duration: Long) {
    Timer().scheduleAtFixedRate(object : TimerTask() {
        override fun run() {
            runOnUiThread {
                uiJob.run()
            }
        }
    }, duration, duration)
}