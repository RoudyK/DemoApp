package com.demo.roudykk.demoapp.injection.module

import com.demo.roudykk.demoapp.UiThread
import com.demo.roudykk.demoapp.ui.activity.MainActivity
import com.roudykk.domain.executor.PostExecutionThread
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class AppModule {

    @Binds
    abstract fun bindExecutionThread(uiThread: UiThread): PostExecutionThread

    @ContributesAndroidInjector
    abstract fun contributeMainActivity(): MainActivity
}