package com.demo.roudykk.demoapp.ui.activity

import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo.SCREEN_ORIENTATION_USER_PORTRAIT
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.app.AppCompatDelegate
import android.view.MenuItem
import com.demo.roudykk.demoapp.R
import com.demo.roudykk.demoapp.extensions.applyPopAnimation
import com.demo.roudykk.demoapp.extensions.applyPushAnimation
import io.github.inflationx.viewpump.ViewPumpContextWrapper

abstract class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        requestedOrientation = SCREEN_ORIENTATION_USER_PORTRAIT
        delegate.setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        super.onCreate(savedInstanceState)
        window.statusBarColor = ContextCompat.getColor(this, R.color.colorPrimaryDark)
    }

    override fun attachBaseContext(newBase: Context?) {
        if (newBase != null) {
            super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase))
        } else {
            super.attachBaseContext(newBase)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> {
                onBackPressed()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        applyPopAnimation()
    }

    override fun startActivity(intent: Intent?) {
        super.startActivity(intent)
        applyPushAnimation()
    }
}