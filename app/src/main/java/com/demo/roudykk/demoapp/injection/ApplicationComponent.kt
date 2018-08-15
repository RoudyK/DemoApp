package com.demo.roudykk.demoapp.injection

import android.app.Application
import com.demo.roudykk.demoapp.DemoApplication
import com.demo.roudykk.demoapp.injection.module.*
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AndroidInjectionModule::class,
    ApplicationModule::class,
    AppModule::class,
    CacheModule::class,
    DataModule::class,
    PresentationModule::class,
    RemoteModule::class
])
interface ApplicationComponent {

    @Component.Builder
    interface builder {

        @BindsInstance
        fun application(application: Application): builder

        fun build(): ApplicationComponent
    }

    fun inject(application: DemoApplication)
}