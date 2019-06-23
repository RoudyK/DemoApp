package com.demo.roudykk.demoapp.features.settings

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.preference.PreferenceFragmentCompat
import com.demo.roudykk.demoapp.MainActivity
import com.demo.roudykk.demoapp.R
import com.demo.roudykk.demoapp.db.PreferenceRepo
import com.demo.roudykk.demoapp.extensions.getThemeWrapper
import com.demo.roudykk.demoapp.ui.fragment.BaseFragmentBuilder

class SettingsFragment : PreferenceFragmentCompat(), BaseFragmentBuilder, SharedPreferences.OnSharedPreferenceChangeListener {

    private lateinit var sContext: Context

    override fun onAttach(context: Context) {
        this.sContext = context
        super.onAttach(context)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return super.onCreateView(inflater.cloneInContext(context.getThemeWrapper()), container, savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
        preferenceManager.sharedPreferences.registerOnSharedPreferenceChangeListener(this)
    }

    override fun onPause() {
        preferenceManager.sharedPreferences.unregisterOnSharedPreferenceChangeListener(this)
        super.onPause()
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
        if (key != PreferenceRepo.PREFERENCE_THEME) {
            return
        }

        activity?.recreate()
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.settings_preferences, rootKey)
    }
}