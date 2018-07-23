package com.demo.roudykk.demoapp.ui.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.demo.roudykk.demoapp.R
import com.demo.roudykk.demoapp.ui.fragment.SettingsFragment
import kotlinx.android.synthetic.main.activity_settings.*

class SettingsActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        title = getString(R.string.settings)

        fragmentManager
                .beginTransaction()
                .replace(R.id.container, SettingsFragment())
                .commitAllowingStateLoss()
    }

    companion object {
        fun launch(context: Context) {
            context.startActivity(Intent(context, SettingsActivity::class.java))
        }
    }
}