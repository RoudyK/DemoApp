package com.demo.roudykk.demoapp.ui.view

import android.content.Context
import android.util.AttributeSet
import android.view.ContextThemeWrapper
import androidx.preference.ListPreference
import com.demo.roudykk.demoapp.R
import com.demo.roudykk.demoapp.extensions.logD

class DemoListPreference : ListPreference {
    constructor(context: Context?) : super(ContextThemeWrapper(context, R.style.DemoAlertDialog)
            .also { logD { "Chose 1" } })
    constructor(context: Context?, attrs: AttributeSet?) : super(ContextThemeWrapper(context, R.style.DemoAlertDialog)
            .also { logD { "Chose 2" } }, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(ContextThemeWrapper(context, R.style.DemoAlertDialog)
            .also { logD { "Chose 3" } }, attrs, defStyleAttr)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int)
            : super(ContextThemeWrapper(context, R.style.DemoAlertDialog)
            .also { logD { "Chose 4" } }, attrs, defStyleAttr, defStyleRes)
}