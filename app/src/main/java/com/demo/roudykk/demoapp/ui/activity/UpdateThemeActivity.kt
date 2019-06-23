package com.demo.roudykk.demoapp.ui.activity

import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.demo.roudykk.demoapp.R

class UpdateThemeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_theme)
        window?.statusBarColor = ContextCompat.getColor(this, R.color.backgroundColorUpdateTheme)

        Handler().postDelayed({
            finish()
            overridePendingTransition(-1, -1)
        }, 2000)
    }

    override fun onBackPressed() {}
}