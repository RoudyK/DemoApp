package com.demo.roudykk.demoapp.extensions

import android.content.Context
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.contract

@ExperimentalContracts
fun Context?.color(@ColorRes colorRes: Int): Int? {
    contract { returnsNotNull() implies (this@color != null) }
    return if (this != null) {
        ContextCompat.getColor(this, colorRes)
    } else {
        null
    }
}