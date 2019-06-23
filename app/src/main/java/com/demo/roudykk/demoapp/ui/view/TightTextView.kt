package com.demo.roudykk.demoapp.ui.view

import android.content.Context
import android.text.Layout
import android.util.AttributeSet
import android.widget.TextView
import kotlin.math.ceil

class TightTextView : TextView {
    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle)

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        val specModeW = MeasureSpec.getMode(widthMeasureSpec)
        if (specModeW != MeasureSpec.EXACTLY) {
            val layout = layout
            if (layout != null) {
                val w = ceil(getMaxLineWidth(layout).toDouble()).toInt() + compoundPaddingLeft + compoundPaddingRight
                if (w < measuredWidth) {
                    super.onMeasure(MeasureSpec.makeMeasureSpec(w, MeasureSpec.AT_MOST),
                            heightMeasureSpec)
                }
            }
        }
    }

    private fun getMaxLineWidth(layout: Layout): Float {
        var maxWidth = 0.0f
        val lines = layout.lineCount
        for (i in 0 until lines) {
            if (layout.getLineWidth(i) > maxWidth) {
                maxWidth = layout.getLineWidth(i)
            }
        }
        return maxWidth
    }
}