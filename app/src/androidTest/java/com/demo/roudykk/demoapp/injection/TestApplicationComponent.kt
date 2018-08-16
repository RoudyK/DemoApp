package com.demo.roudykk.demoapp.injection

import android.app.Application
import com.demo.roudykk.demoapp.TestApplication
import com.demo.roudykk.demoapp.injection.module.*
import com.roudykk.domain.repository.MoviesRepository
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AndroidInjectionModule::class,
    TestApplicationModule::class,
    TestCacheModule::class,
    TestDataModule::class,
    TestRemoteModule::class,
    AppModule::class,
    PresentationModule::class
])
interface TestApplicationComponent {

    fun moviesRepository(): MoviesRepository

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): TestApplicationComponent.Builder

        fun build(): TestApplicationComponent
    }

    fun inject(app: TestApplication)
}