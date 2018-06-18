package com.demo.roudykk.demoapp.ui.view

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import android.widget.TextView
import io.github.inflationx.calligraphy3.CalligraphyUtils


class FancyTextView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : TextView(context, attrs, defStyleAttr) {

    init {
        typeface = Typeface.createFromAsset(context.assets, "fonts/Main.ttf")
        CalligraphyUtils.applyFontToTextView(this, typeface)
    }
}
