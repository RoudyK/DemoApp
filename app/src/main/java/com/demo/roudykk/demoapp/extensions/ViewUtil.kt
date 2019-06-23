package com.demo.roudykk.demoapp.extensions

import android.content.Context
import android.graphics.PorterDuff
import android.graphics.drawable.LayerDrawable
import android.preference.PreferenceManager
import android.view.ContextThemeWrapper
import android.widget.RatingBar
import androidx.core.content.ContextCompat
import com.demo.roudykk.demoapp.R
import com.demo.roudykk.demoapp.db.PreferenceRepo


fun RatingBar.applyTheme() {
    val stars = progressDrawable as LayerDrawable
    stars.getDrawable(0).setColorFilter(ContextCompat.getColor(context, R.color.colorRating), PorterDuff.Mode.SRC_ATOP)
}


fun Context?.getThemeWrapper(): ContextThemeWrapper {
    val selectedTheme = PreferenceManager.getDefaultSharedPreferences(this)
            .getString(PreferenceRepo.PREFERENCE_THEME, PreferenceRepo.Theme.LIGHT.value)
    val theme = PreferenceRepo.Theme.values().firstOrNull { it.value == selectedTheme }
    return if (theme != null) {
        when (theme) {
            PreferenceRepo.Theme.LIGHT -> {
                ContextThemeWrapper(this, R.style.AppTheme)
            }
            PreferenceRepo.Theme.DARK -> {
                ContextThemeWrapper(this, R.style.AppTheme_Dark)
            }
            PreferenceRepo.Theme.BURGUNDY -> {
                ContextThemeWrapper(this, R.style.AppTheme_Burgundy)
            }
        }
    } else {
        ContextThemeWrapper(this, R.style.AppTheme)
    }
}