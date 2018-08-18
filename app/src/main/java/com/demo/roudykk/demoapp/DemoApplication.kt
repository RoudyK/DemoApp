package com.demo.roudykk.demoapp

import android.app.Activity
import android.app.Application
import android.content.Context
import android.support.v4.app.Fragment
import android.support.v7.widget.SnapHelper
import com.airbnb.epoxy.Carousel
import com.crashlytics.android.Crashlytics
import com.demo.roudykk.demoapp.controllers.helpers.StartSnapHelper
import com.demo.roudykk.demoapp.injection.DaggerApplicationComponent
import com.google.android.gms.ads.MobileAds
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import dagger.android.support.HasSupportFragmentInjector
import io.fabric.sdk.android.Fabric
import io.github.inflationx.calligraphy3.CalligraphyConfig
import io.github.inflationx.calligraphy3.CalligraphyInterceptor
import io.github.inflationx.viewpump.ViewPump
import javax.inject.Inject


class DemoApplication : Application(), HasActivityInjector, HasSupportFragmentInjector {

    @Inject
    lateinit var activityInjector: DispatchingAndroidInjector<Activity>

    @Inject
    lateinit var fragmentInjector: DispatchingAndroidInjector<Fragment>

    override fun activityInjector(): AndroidInjector<Activity> {
        return this.activityInjector
    }

    override fun supportFragmentInjector(): AndroidInjector<Fragment> {
        return this.fragmentInjector
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

        Carousel.setDefaultGlobalSnapHelperFactory(object : Carousel.SnapHelperFactory() {
            override fun buildSnapHelper(context: Context?): SnapHelper {
                return StartSnapHelper()
            }
        })

        MobileAds.initialize(this, getString(R.string.ad_app_id))
    }
}