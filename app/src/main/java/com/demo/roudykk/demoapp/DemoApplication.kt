package com.demo.roudykk.demoapp

import android.app.Application
import io.github.inflationx.calligraphy3.CalligraphyConfig
import io.github.inflationx.calligraphy3.CalligraphyInterceptor
import io.github.inflationx.viewpump.ViewPump


class DemoApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        ViewPump.init(ViewPump.builder()
                .addInterceptor(CalligraphyInterceptor(
                        CalligraphyConfig.Builder()
                                .setDefaultFontPath("fonts/AirbnbCereal-Medium.ttf")
                                .setFontAttrId(R.attr.fontPath)
                                .build()))
                .build())
    }
}