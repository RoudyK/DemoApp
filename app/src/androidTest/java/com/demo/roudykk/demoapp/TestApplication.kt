package com.demo.roudykk.demoapp

import android.app.Activity
import android.app.Application
import androidx.test.InstrumentationRegistry
import androidx.core.app.Fragment
import com.demo.roudykk.demoapp.injection.DaggerTestApplicationComponent
import com.demo.roudykk.demoapp.injection.TestApplicationComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject

class TestApplication : Application(), HasActivityInjector, HasSupportFragmentInjector {

    @Inject
    lateinit var activityInjector: DispatchingAndroidInjector<Activity>

    @Inject
    lateinit var fragmentInjector: DispatchingAndroidInjector<Fragment>

    private lateinit var appComponent: TestApplicationComponent

    override fun activityInjector(): AndroidInjector<Activity> {
        return this.activityInjector
    }

    override fun supportFragmentInjector(): AndroidInjector<Fragment> {
        return this.fragmentInjector
    }

    companion object {
        fun appComponent(): TestApplicationComponent {
            return (InstrumentationRegistry.getTargetContext().applicationContext
                    as TestApplication).appComponent
        }
    }

    override fun onCreate() {
        super.onCreate()

        this.appComponent = DaggerTestApplicationComponent
                .builder()
                .application(this)
                .build()
        this.appComponent.inject(this)
    }

}