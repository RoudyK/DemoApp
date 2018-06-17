package com.demo.roudykk.demoapp.ui.view

import android.content.Context
import android.util.AttributeSet
import android.widget.RelativeLayout
import com.demo.roudykk.demoapp.R
import kotlinx.android.synthetic.main.action_button.view.*


class ActionButton @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : RelativeLayout(context, attrs, defStyleAttr) {

    init {
        inflate(context, R.layout.action_button, this)

        val attributes = context.obtainStyledAttributes(attrs, R.styleable.ActionButton, defStyleAttr, 0)

        val text = attributes.getString(R.styleable.ActionButton_android_text)
        val textAllCaps = attributes.getBoolean(R.styleable.ActionButton_android_textAllCaps, false)

        actionView.text = text
        actionView.setAllCaps(textAllCaps)

        attributes.recycle()
    }
}