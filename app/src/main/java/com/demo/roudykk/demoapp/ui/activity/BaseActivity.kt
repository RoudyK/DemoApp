package com.demo.roudykk.demoapp.ui.activity

import android.content.pm.ActivityInfo.SCREEN_ORIENTATION_USER_PORTRAIT
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.demo.roudykk.demoapp.R
import com.demo.roudykk.demoapp.db.PreferenceRepo

abstract class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        requestedOrientation = SCREEN_ORIENTATION_USER_PORTRAIT
        val selectedTheme = PreferenceManager.getDefaultSharedPreferences(this)
                .getString(PreferenceRepo.PREFERENCE_THEME, PreferenceRepo.Theme.LIGHT.value)

        val theme = PreferenceRepo.Theme.values().firstOrNull { it.value == selectedTheme }
        if (theme != null) {
            when (theme) {
                PreferenceRepo.Theme.LIGHT -> {
                    setTheme(R.style.AppTheme)
                    delegate.localNightMode = AppCompatDelegate.MODE_NIGHT_NO
                }
                PreferenceRepo.Theme.DARK -> {
                    setTheme(R.style.AppTheme_Dark)
                    delegate.localNightMode = AppCompatDelegate.MODE_NIGHT_YES
                }
                PreferenceRepo.Theme.BURGUNDY -> {
                    setTheme(R.style.AppTheme_Burgundy)
                    delegate.localNightMode = AppCompatDelegate.MODE_NIGHT_YES
                }
            }
        } else {
            delegate.localNightMode = AppCompatDelegate.MODE_NIGHT_NO
        }
        super.onCreate(savedInstanceState)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> {
                onBackPressed()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}