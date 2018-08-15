package com.demo.roudykk.demoapp

import android.app.Activity
import android.app.Application
import com.crashlytics.android.Crashlytics
import com.demo.roudykk.demoapp.injection.DaggerApplicationComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import io.fabric.sdk.android.Fabric
import io.github.inflationx.calligraphy3.CalligraphyConfig
import io.github.inflationx.calligraphy3.CalligraphyInterceptor
import io.github.inflationx.viewpump.ViewPump
import javax.inject.Inject


class DemoApplication : Application(), HasActivityInjector {

    @Inject
    lateinit var injector: DispatchingAndroidInjector<Activity>

    override fun activityInjector(): AndroidInjector<Activity> {
        return this.injector
    }

    override fun onCreate() {
        super.onCreate()

        DaggerApplicationComponent.builder()
                .application(this)
                .build()
                .inject(this)

        ViewPump.init(ViewPump.builder()
                .addInterceptor(CalligraphyInterceptor(
                        CalligraphyConfig.Builder()
                                .setDefaultFontPath("fonts/AirbnbCereal-Light.ttf")
                                .setFontAttrId(R.attr.fontPath)
                                .build()))
                .build())

        if (!BuildConfig.DEBUG) {
            Fabric.with(this, Crashlytics())
        }
    }
}