package com.demo.roudykk.demoapp.injection.module

import android.app.Application
import android.content.Context
import dagger.Binds
import dagger.Module

@Module
@Suppress("unused")
abstract class ApplicationModule {

    @Binds
    abstract fun bindContext(application: Application): Context
}