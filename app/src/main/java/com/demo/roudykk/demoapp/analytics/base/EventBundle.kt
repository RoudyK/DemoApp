package com.demo.roudykk.demoapp.analytics.base

import android.os.Bundle
import com.demo.roudykk.demoapp.analytics.consts.Param

class EventBundle {
    private var bundle: Bundle = Bundle()

    fun put(param: Param, value: Any) {
        bundle.putString(param.value, value.toString())
    }

    fun toBundle(): Bundle {
        return bundle
    }
}